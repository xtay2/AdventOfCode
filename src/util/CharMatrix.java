package util;


import java.util.*;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class CharMatrix implements Cloneable {

    private final char[][] matrix;

    public CharMatrix(int width, int height, char fill) {
        matrix = new char[height][width];
        for (var row : matrix)
            Arrays.fill(row, fill);
    }

    public CharMatrix(char[][] matrix) {
        this.matrix = matrix;
    }

    @Override
    @SuppressWarnings({"All"})
    public CharMatrix clone() {
        return new CharMatrix(matrix.clone());
    }

    public boolean isInBounds(IPoint p) {
        return isInBounds(p.x(), p.y());
    }

    public boolean isInBounds(int x, int y) {
        return 0 <= x && x < width() && 0 <= y && y < height();
    }

    public Character get(IPoint p) {
        return get(p.x(), p.y());
    }

    public Character get(int x, int y) {
        return isInBounds(x, y)
                ? matrix[y][x]
                : null;
    }

    public CoordValChar at(IPoint p) {
        var val = get(p);
        return val == null ? null : new CoordValChar(p, val);
    }

    public CoordValChar at(int x, int y) {
        var val = get(x, y);
        return val == null ? null : new CoordValChar(x, y, val);
    }

    public void set(IPoint p, char val) {
        set(p.x(), p.y(), val);
    }

    public void set(int x, int y, char val) {
        if (!isInBounds(x, y))
            throw new IllegalArgumentException("[" + x + ", " + y + "] is not in bounds.");
        matrix[y][x] = val;
    }

    public int height() {
        return matrix.length;
    }

    public int width() {
        return matrix[0].length;
    }

    public char[] row(int y) {
        return matrix[y].clone();
    }

    public char[] col(int x) {
        var res = Arrays.copyOf(matrix[0], height());
        for (int y = 0; y < height(); y++)
            res[y] = matrix[y][x];
        return res;
    }

    /**
     * Performs a depth first search on this matrix.
     *
     * @param x       the starting x coordinate
     * @param y       the starting y coordinate
     * @param inspect predicate that returns true if the passed coord should get visited.
     * @param action  an action, performed after the check.
     * @return the visited points
     */
    public Set<IPoint> dfs(int x, int y, CoordValCharPredicate inspect, CoordValCharConsumer action) {
        return dfs(new IPoint(x, y), inspect, action);
    }

    /**
     * Performs a depth first search on this matrix.
     *
     * @param p       the starting coordinates
     * @param inspect predicate that returns true if the passed coord should get visited.
     * @param action  an action, performed after the check.
     * @return the visited points
     */
    public Set<IPoint> dfs(IPoint p, CoordValCharPredicate inspect, CoordValCharConsumer action) {
        return Set.copyOf(dfs(p, inspect, action, new HashSet<>()));
    }

    private Set<IPoint> dfs(IPoint p, CoordValCharPredicate inspect, CoordValCharConsumer action, Set<IPoint> visited) {
        var val = get(p);
        if (visited.contains(p) || !inspect.test(p, val))
            return visited;
        visited.add(p);
        action.accept(p, val);
        neighbours(p).forEach(neighbour -> dfs(neighbour, inspect, action, visited));
        return visited;
    }

    /**
     * Calculates the path that visits the fewest points from start to end using dijkstra's algorithm.
     *
     * @param x1      the starting x coordinate
     * @param y1      the starting y coordinate
     * @param x2      the target x coordinate
     * @param y2      the target y coordinate
     * @param inspect predicate that returns true if the passed coord should get visited.
     * @param action  an action, performed after the check.
     * @return the visited points on the shortest path or {@code null} if no path was found.
     */
    public SequencedSet<IPoint> shortestPath(int x1, int y1, int x2, int y2, CoordValCharPredicate inspect, CoordValCharConsumer action) {
        return shortestPath(new IPoint(x1, y1), new IPoint(x2, y2), inspect, action);
    }

    /**
     * Calculates the path that visits the fewest points from start to end using dijkstra's algorithm.
     *
     * @param start   the starting coordinates
     * @param end     the target coordinates
     * @param inspect predicate that returns true if the passed coord should get visited.
     * @param action  an action, performed after the check.
     * @return the visited points on the shortest path or {@code null} if no path was found.
     */
    public SequencedSet<IPoint> shortestPath(IPoint start, IPoint end, CoordValCharPredicate inspect, CoordValCharConsumer action) {
        var res = shortestPath(start, end, inspect);
        if (res != null)
            res.forEach(p -> action.accept(p, get(p)));
        return res;
    }

    public SequencedSet<IPoint> shortestPath(IPoint start, IPoint end, CoordValCharPredicate inspect) {
        record Node(IPoint point, int distance) {}
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
        Map<IPoint, Integer> distances = new HashMap<>();
        Map<IPoint, IPoint> previous = new HashMap<>();
        Set<IPoint> visited = new HashSet<>();

        queue.add(new Node(start, 0));
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            var current = queue.poll();
            var currentPoint = current.point();

            if (visited.contains(currentPoint))
                continue;
            visited.add(currentPoint);

            if (currentPoint.equals(end)) {
                SequencedSet<IPoint> path = new LinkedHashSet<>();
                for (var at = end; at != null; at = previous.get(at)) {
                    path.addFirst(at);
                }
                return path;
            }

            for (IPoint neighbor : neighbours(currentPoint).toList()) {
                if (!inspect.test(neighbor, get(neighbor))) {
                    continue;
                }

                int newDist = distances.get(currentPoint) + 1;
                if (newDist < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, currentPoint);
                    queue.add(new Node(neighbor, newDist));
                }
            }
        }

        return null; // No path found
    }

    public Stream<IPoint> neighbours(int x, int y) {
        return neighbours(new IPoint(x, y));
    }

    public Stream<IPoint> neighbours(IPoint p) {
        return p.neighbours().filter(this::isInBounds);
    }

    public void foreach(CoordValCharConsumer consumer) {
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                consumer.accept(x, y, get(x, y));
            }
        }
    }

    public Stream<CoordValChar> stream() {
        var strBuilder = Stream.<CoordValChar>builder();
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                strBuilder.add(at(x, y));
            }
        }
        return strBuilder.build();
    }

    @Deprecated
    public <U> Stream<U> map(CoordValCharFunction<U> function) {
        var lst = new ArrayList<U>();
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                lst.add(function.apply(at(x, y)));
            }
        }
        return lst.stream();
    }

    public IntMatrix mapToInt(CoordValCharToIntFunction function) {
        var mat = new IntMatrix(width(), height());
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                mat.set(x, y, function.apply(at(x, y)));
            }
        }
        return mat;
    }

    public LongMatrix mapToLong(CoordValCharToLongFunction function) {
        var mat = new LongMatrix(width(), height());
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                mat.set(x, y, function.apply(x, y, get(x, y)));
            }
        }
        return mat;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (var line : matrix)
            sb.append(Arrays.toString(line)).append("\n");
        return sb.toString();
    }

    public IPoint findFirst(char s) {
        return stream().filter((cvc) -> cvc.val() == s)
                .findFirst()
                .map(CharMatrix.CoordValChar::pos)
                .orElse(null);
    }

    @FunctionalInterface
    public interface CoordValCharConsumer {
        void accept(int x, int y, char val);

        default void accept(IPoint p, char val) {
            accept(p.x(), p.y(), val);
        }

        default void accept(CoordValChar cvc) {
            accept(cvc.x(), cvc.y(), cvc.val());
        }
    }

    @FunctionalInterface
    public interface CoordValCharFunction<T> {
        T apply(int x, int y, char val);

        default T apply(IPoint p, char val) {
            return apply(p.x(), p.y(), val);
        }

        default T apply(CoordValChar cvc) {
            return apply(cvc.x(), cvc.y(), cvc.val());
        }
    }

    @FunctionalInterface
    public interface CoordValCharPredicate {
        boolean test(int x, int y, char val);

        default boolean test(IPoint p, char val) {
            return test(p.x(), p.y(), val);
        }

        default boolean test(CoordValChar cvc) {
            return test(cvc.x(), cvc.y(), cvc.val());
        }
    }

    @FunctionalInterface
    public interface CoordValCharToIntFunction {
        int apply(int x, int y, char val);

        default int apply(IPoint p, char val) {
            return apply(p.x(), p.y(), val);
        }

        default int apply(CoordValChar cvc) {
            return apply(cvc.x(), cvc.y(), cvc.val());
        }
    }

    @FunctionalInterface
    public interface CoordValCharToLongFunction {
        long apply(int x, int y, char val);

        default long apply(IPoint p, char val) {
            return apply(p.x(), p.y(), val);
        }
    }

    public record CoordValChar(int x, int y, char val) {

        public CoordValChar(IPoint pos, char val) {
            this(pos.x(), pos.y(), val);
        }

        public IPoint pos() {
            return new IPoint(x, y);
        }
    }

}