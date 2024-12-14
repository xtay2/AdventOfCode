package util;

import java.util.stream.Stream;

public sealed interface Point permits IPoint, LPoint {

    Point up();

    Point down();

    Point left();

    Point right();

    Stream<? extends Point> neighbours();

    Stream<? extends Point> surrounding();

}