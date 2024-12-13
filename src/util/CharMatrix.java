package util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class CharMatrix {

    private final char[][] matrix;

    public CharMatrix(int width, int height) {
        matrix = new char[height][width];
    }

    public CharMatrix(char[][] matrix) {
        this.matrix = matrix;
    }

    public boolean isInBounds(Point p) {
        return isInBounds(p.x(), p.y());
    }

    public boolean isInBounds(int x, int y) {
        return 0 <= x && x < width() && 0 <= y && y < height();
    }

    public Character get(Point p) {
        return get(p.x(), p.y());
    }

    public Character get(int x, int y) {
        return isInBounds(x, y)
                ? matrix[y][x]
                : null;
    }

    public void set(Point p, char val) {
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
    public Set<Point> dfs(int x, int y, CoordValCharPredicate inspect, CoordValCharConsumer action) {
        return dfs(new Point(x, y), inspect, action);
    }

    /**
     * Performs a depth first search on this matrix.
     *
     * @param p       the starting coordinates
     * @param inspect predicate that returns true if the passed coord should get visited.
     * @param action  an action, performed after the check.
     * @return the visited points
     */
    public Set<Point> dfs(Point p, CoordValCharPredicate inspect, CoordValCharConsumer action) {
        return Set.copyOf(dfs(p, inspect, action, new HashSet<>()));
    }

    private Set<Point> dfs(Point p, CoordValCharPredicate inspect, CoordValCharConsumer action, Set<Point> visited) {
        var val = get(p);
        if (visited.contains(p) || !inspect.test(p, val))
            return visited;
        visited.add(p);
        action.accept(p, val);
        for (var neighbour : neighbours(p))
            dfs(neighbour, inspect, action, visited);
        return visited;
    }

    public Set<Point> neighbours(int x, int y) {
        return neighbours(new Point(x, y));
    }

    public Set<Point> neighbours(Point p) {
        return p.neighbours().filter(this::isInBounds).collect(Collectors.toUnmodifiableSet());
    }

    public void foreach(CoordValCharConsumer consumer) {
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                consumer.accept(x, y, get(x, y));
            }
        }
    }

    public <U> Stream<U> map(CoordValCharFunction<U> function) {
        var lst = new ArrayList<U>();
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                lst.add(function.apply(x, y, get(x, y)));
            }
        }
        return lst.stream();
    }

    public IntMatrix mapToInt(CoordValCharToIntFunction function) {
        var mat = new IntMatrix(width(), height());
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                mat.set(x, y, function.apply(x, y, get(x, y)));
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

    @FunctionalInterface
    public interface CoordValCharConsumer {
        void accept(int x, int y, char val);

        default void accept(Point p, char val) {
            accept(p.x(), p.y(), val);
        }
    }

    @FunctionalInterface
    public interface CoordValCharFunction<T> {
        T apply(int x, int y, char val);

        default T apply(Point p, char val) {
            return apply(p.x(), p.y(), val);
        }
    }

    @FunctionalInterface
    public interface CoordValCharPredicate {
        boolean test(int x, int y, char val);

        default boolean test(Point p, char val) {
            return test(p.x(), p.y(), val);
        }
    }

    @FunctionalInterface
    public interface CoordValCharToIntFunction {
        int apply(int x, int y, char val);

        default int apply(Point p, char val) {
            return apply(p.x(), p.y(), val);
        }
    }

    @FunctionalInterface
    public interface CoordValCharToLongFunction {
        long apply(int x, int y, char val);

        default long apply(Point p, char val) {
            return apply(p.x(), p.y(), val);
        }
    }

}