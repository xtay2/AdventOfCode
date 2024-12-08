package year2024.day08;

import java.util.ArrayList;
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
        var res = new char[height()];
        for (int y = 0; y < height(); y++)
            res[y] = matrix[y][x];
        return res;
    }

    public void foreach(CoordValConsumer consumer) {
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                consumer.accept(x, y, get(x, y));
            }
        }
    }

    public <T> Stream<T> map(CoordValFunction<T> function) {
        var lst = new ArrayList<T>();
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                lst.add(function.apply(x, y, get(x, y)));
            }
        }
        return lst.stream();
    }

    @FunctionalInterface
    public interface CoordValConsumer {
        void accept(int x, int y, char c);
    }

    @FunctionalInterface
    public interface CoordValFunction<T> {
        T apply(int x, int y, char c);
    }

}