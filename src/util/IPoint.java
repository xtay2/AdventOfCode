package util;

import java.util.stream.Stream;

public record IPoint(int x, int y) implements Point {

    public IPoint up() {
        return new IPoint(x, y - 1);
    }

    public IPoint down() {
        return new IPoint(x, y + 1);
    }

    public IPoint left() {
        return new IPoint(x - 1, y);
    }

    public IPoint right() {
        return new IPoint(x + 1, y);
    }

    public IPoint neighbour(Direction d) {
        return switch (d) {
            case UP -> up();
            case DOWN -> down();
            case LEFT -> left();
            case RIGHT -> right();
        };
    }

    public Stream<IPoint> neighbours() {
        return Stream.of(up(), down(), left(), right());
    }

    public Stream<IPoint> surrounding() {
        return Stream.concat(neighbours(), Stream.of(
                new IPoint(x - 1, y - 1),
                new IPoint(x + 1, y - 1),
                new IPoint(x - 1, y + 1),
                new IPoint(x + 1, y + 1)
        ));
    }

    public int manhattanDist(IPoint p) {
        return Math.abs(x - p.x) + Math.abs(y - p.y);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    public Point plus(Point p) {
        return switch (p) {
            case IPoint(var ix, var iy) -> new IPoint(x + ix, y + iy);
            case LPoint(var lx, var ly) -> new LPoint(x + lx, y + ly);
        };
    }

}