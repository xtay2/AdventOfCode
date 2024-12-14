package util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class IntMatrix {

    private final int[][] matrix;

    public IntMatrix(int width, int height) {
        matrix = new int[height][width];
    }

    public IntMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public boolean isInBounds(IPoint p) {
        return isInBounds(p.x(), p.y());
    }

    public boolean isInBounds(int x, int y) {
        return 0 <= x && x < width() && 0 <= y && y < height();
    }

    public Integer get(IPoint p) {
        return get(p.x(), p.y());
    }

    public Integer get(int x, int y) {
        return isInBounds(x, y)
                ? matrix[y][x]
                : null;
    }

    public void set(IPoint p, int val) {
        set(p.x(), p.y(), val);
    }

    public void set(int x, int y, int val) {
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

    public int[] row(int y) {
        return matrix[y].clone();
    }

    public int[] col(int x) {
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
    public Set<IPoint> dfs(int x, int y, CoordValIntPredicate inspect, CoordValIntConsumer action) {
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
    public Set<IPoint> dfs(IPoint p, CoordValIntPredicate inspect, CoordValIntConsumer action) {
        return Set.copyOf(dfs(p, inspect, action, new HashSet<>()));
    }

    private Set<IPoint> dfs(IPoint p, CoordValIntPredicate inspect, CoordValIntConsumer action, Set<IPoint> visited) {
        var val = get(p);
        if (visited.contains(p) || !inspect.test(p, val))
            return visited;
        visited.add(p);
        action.accept(p, val);
        neighbours(p).forEach(neighbour -> dfs(neighbour, inspect, action, visited));
        return visited;
    }

    public Stream<IPoint> neighbours(int x, int y) {
        return neighbours(new IPoint(x, y));
    }

    public Stream<IPoint> neighbours(IPoint p) {
        return p.neighbours().filter(this::isInBounds);
    }

    public void foreach(CoordValIntConsumer consumer) {
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                consumer.accept(x, y, get(x, y));
            }
        }
    }

    public <U> Stream<U> map(CoordValIntFunction<U> function) {
        var lst = new ArrayList<U>();
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                lst.add(function.apply(x, y, get(x, y)));
            }
        }
        return lst.stream();
    }

    public LongMatrix mapToLong(CoordValIntToLongFunction function) {
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

    @FunctionalInterface
    public interface CoordValIntConsumer {
        void accept(int x, int y, int val);

        default void accept(IPoint p, int val) {
            accept(p.x(), p.y(), val);
        }
    }

    @FunctionalInterface
    public interface CoordValIntFunction<T> {
        T apply(int x, int y, int val);

        default T apply(IPoint p, int val) {
            return apply(p.x(), p.y(), val);
        }
    }

    @FunctionalInterface
    public interface CoordValIntPredicate {
        boolean test(int x, int y, int val);

        default boolean test(IPoint p, int val) {
            return test(p.x(), p.y(), val);
        }
    }

    @FunctionalInterface
    public interface CoordValIntToLongFunction {
        long apply(int x, int y, int val);

        default long apply(IPoint p, int val) {
            return apply(p.x(), p.y(), val);
        }
    }
}