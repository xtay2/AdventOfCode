package util;

import java.util.stream.Stream;

public record LPoint(long x, long y) implements Point {

    /** Parse the expression "x,y" into a point. */
    public static LPoint fromCSV(String csv) {
        var split = csv.split(",");
        return new LPoint(Long.parseLong(split[0]), Long.parseLong(split[1]));
    }

    public LPoint up() {
        return new LPoint(x, y - 1);
    }

    public LPoint down() {
        return new LPoint(x, y + 1);
    }

    public LPoint left() {
        return new LPoint(x - 1, y);
    }

    public LPoint right() {
        return new LPoint(x + 1, y);
    }

    public LPoint neighbour(Direction d) {
        return switch (d) {
            case UP -> up();
            case DOWN -> down();
            case LEFT -> left();
            case RIGHT -> right();
        };
    }

    public Stream<LPoint> neighbours() {
        return Stream.of(up(), down(), left(), right());
    }

    public Stream<LPoint> surrounding() {
        return Stream.concat(neighbours(), Stream.of(
                new LPoint(x - 1, y - 1),
                new LPoint(x + 1, y - 1),
                new LPoint(x - 1, y + 1),
                new LPoint(x + 1, y + 1)
        ));
    }

    public long manhattanDist(LPoint p) {
        return Math.abs(x - p.x) + Math.abs(y - p.y);
    }

    public IPoint toIPoint() {
        return x instanceof int ix && y instanceof int iy ? new IPoint(ix, iy) : null;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    public LPoint plus(Point p) {
        return switch (p) {
            case IPoint(var ix, var iy) -> new LPoint(x + ix, y + iy);
            case LPoint(var lx, var ly) -> new LPoint(x + lx, y + ly);
        };
    }

    public LPoint modulo(Point p) {
        return switch (p) {
            case IPoint(var ix, var iy) -> new LPoint(x % ix, y % iy);
            case LPoint(var lx, var ly) -> new LPoint(x % lx, y % ly);
        };
    }

    public LPoint times(long n) {
        return new LPoint(x * n, y * n);
    }

}