package util;

import java.util.stream.Stream;

public record Point(int x, int y) {

    public Point up() {
        return new Point(x, y - 1);
    }

    public Point down() {
        return new Point(x, y + 1);
    }

    public Point left() {
        return new Point(x - 1, y);
    }

    public Point right() {
        return new Point(x + 1, y);
    }

    public Point neighbour(Direction d) {
        return switch (d) {
            case UP -> up();
            case DOWN -> down();
            case LEFT -> left();
            case RIGHT -> right();
        };
    }

    public Stream<Point> neighbours() {
        return Stream.of(up(), down(), left(), right());
    }

    public Stream<Point> surrounding() {
        return Stream.concat(neighbours(), Stream.of(
                new Point(x - 1, y - 1),
                new Point(x + 1, y - 1),
                new Point(x - 1, y + 1),
                new Point(x + 1, y + 1)
        ));
    }

    public int manhattanDist(Point p) {
        return Math.abs(x - p.x) + Math.abs(y - p.y);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}