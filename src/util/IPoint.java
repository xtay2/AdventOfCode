package util;

import java.util.function.IntUnaryOperator;
import java.util.stream.Stream;

public record IPoint(int x, int y) implements Point {

    /**
     * Parse the expression "x,y" into a point.
     */
    public static IPoint fromCSV(String csv) {
        var split = csv.split(",");
        return new IPoint(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

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

    public Direction neighbourAt(IPoint p) {
        if (up().equals(p))
            return Direction.UP;
        if (down().equals(p))
            return Direction.DOWN;
        if (left().equals(p))
            return Direction.LEFT;
        if (right().equals(p))
            return Direction.RIGHT;
        return null;
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

    public IPoint times(int n) {
        return new IPoint(x * n, y * n);
    }


    public <T extends Point> T plus(T p) {
        return (T) switch (p) {
            case IPoint(var ix, var iy) -> new IPoint(x + ix, y + iy);
            case LPoint(var lx, var ly) -> new LPoint(x + lx, y + ly);
        };
    }

    public <T extends Point> T modulo(T p) {
        return (T) switch (p) {
            case IPoint(var ix, var iy) -> new IPoint(x % ix, y % iy);
            case LPoint(var lx, var ly) -> new LPoint(x % lx, y % ly);
        };
    }

    public IPoint map(IntUnaryOperator fx, IntUnaryOperator fy) {
        return new IPoint(fx.applyAsInt(x), fy.applyAsInt(y));
    }
}