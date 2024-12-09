package util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class LongMatrix {

    private final long[][] matrix;

    public LongMatrix(int width, int height) {
        this.matrix = new long[height][width];
    }

    public LongMatrix(long[][] matrix) {
        this.matrix = matrix;
    }

    public boolean isInBounds(int x, int y) {
        return 0 <= x && x < width() && 0 <= y && y < height();
    }

    public Long get(int x, int y) {
        return isInBounds(x, y)
                ? matrix[y][x]
                : null;
    }

    public void set(int x, int y, long val) {
        if (isInBounds(x, y))
            throw new IllegalArgumentException("[" + x + ", " + y + "] is not in bounds.");
        matrix[y][x] = val;
    }

    public int height() {
        return matrix.length;
    }

    public int width() {
        return matrix[0].length;
    }

    public long[] row(int y) {
        return matrix[y].clone();
    }

    public long[] col(int x) {
        var res = Arrays.copyOf(matrix[0], height());
        for (int y = 0; y < height(); y++)
            res[y] = matrix[y][x];
        return res;
    }

    public void foreach(CoordValLongConsumer consumer) {
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                consumer.accept(x, y, get(x, y));
            }
        }
    }

    public <U> Stream<U> map(CoordValLongFunction<U> function) {
        var lst = new ArrayList<U>();
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                lst.add(function.apply(x, y, get(x, y)));
            }
        }
        return lst.stream();
    }

    @FunctionalInterface
    public interface CoordValLongConsumer {
        void accept(long x, long y, long val);
    }

    @FunctionalInterface
    public interface CoordValLongFunction<T> {
        T apply(long x, long y, long val);
    }

}