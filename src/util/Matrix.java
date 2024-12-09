package util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Matrix<T> {

    private final T[][] matrix;

    public Matrix(T[][] matrix) {
        this.matrix = matrix;
    }

    public boolean isInBounds(int x, int y) {
        return 0 <= x && x < width() && 0 <= y && y < height();
    }

    public T get(int x, int y) {
        return isInBounds(x, y)
                ? matrix[y][x]
                : null;
    }

    public int height() {
        return matrix.length;
    }

    public int width() {
        return matrix[0].length;
    }

    public T[] row(int y) {
        return matrix[y].clone();
    }

    public T[] col(int x) {
        var res = Arrays.copyOf(matrix[0], height());
        for (int y = 0; y < height(); y++)
            res[y] = matrix[y][x];
        return res;
    }

    public void foreach(CoordValConsumer<T> consumer) {
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                consumer.accept(x, y, get(x, y));
            }
        }
    }

    public <U> Stream<U> map(CoordValFunction<T, U> function) {
        var lst = new ArrayList<U>();
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                lst.add(function.apply(x, y, get(x, y)));
            }
        }
        return lst.stream();
    }

    @FunctionalInterface
    public interface CoordValConsumer<T> {
        void accept(int x, int y, T val);
    }

    @FunctionalInterface
    public interface CoordValFunction<T, U> {
        U apply(int x, int y, T val);
    }

}