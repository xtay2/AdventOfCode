package util;


import java.util.ArrayList;
import java.util.Arrays;
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

    public boolean isInBounds(int x, int y) {
        return 0 <= x && x < width() && 0 <= y && y < height();
    }

    public Integer get(int x, int y) {
        return isInBounds(x, y)
                ? matrix[y][x]
                : null;
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

    @FunctionalInterface
    public interface CoordValIntConsumer {
        void accept(int x, int y, int val);
    }

    @FunctionalInterface
    public interface CoordValIntFunction<T> {
        T apply(int x, int y, int val);
    }

}