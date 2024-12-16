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
        for (var neighbour : neighbours(p))
            dfs(neighbour, inspect, action, visited);
        return visited;
    }

    public Set<IPoint> neighbours(int x, int y) {
        return neighbours(new IPoint(x, y));
    }

    public Set<IPoint> neighbours(IPoint p) {
        return p.neighbours().filter(this::isInBounds).collect(Collectors.toUnmodifiableSet());
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
                strBuilder.add(new CoordValChar(x, y, get(x, y)));
            }
        }
        return strBuilder.build();
    }

    @Deprecated
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
    }

    @FunctionalInterface
    public interface CoordValCharFunction<T> {
        T apply(int x, int y, char val);

        default T apply(IPoint p, char val) {
            return apply(p.x(), p.y(), val);
        }
    }

    @FunctionalInterface
    public interface CoordValCharPredicate {
        boolean test(int x, int y, char val);

        default boolean test(IPoint p, char val) {
            return test(p.x(), p.y(), val);
        }
    }

    @FunctionalInterface
    public interface CoordValCharToIntFunction {
        int apply(int x, int y, char val);

        default int apply(IPoint p, char val) {
            return apply(p.x(), p.y(), val);
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
        public IPoint pos() {
            return new IPoint(x, y);
        }
    }

}