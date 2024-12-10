package util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class CharMatrix {

    private final char[][] matrix;

    public CharMatrix(char[][] matrix) {
        this.matrix = matrix;
    }

    public boolean isInBounds(int x, int y) {
        return 0 <= x && x < width() && 0 <= y && y < height();
    }

    public Character get(int x, int y) {
        return isInBounds(x, y)
                ? matrix[y][x]
                : null;
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
    }

    @FunctionalInterface
    public interface CoordValCharFunction<T> {
        T apply(int x, int y, char val);
    }

    @FunctionalInterface
    public interface CoordValCharToIntFunction {
        int apply(int x, int y, char val);
    }

    @FunctionalInterface
    public interface CoordValCharToLongFunction {
        long apply(int x, int y, char val);
    }


}