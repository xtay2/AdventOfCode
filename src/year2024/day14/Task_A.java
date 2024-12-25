package year2024.day14;

import aoc.AdventOfCode;
import aoc.Task;
import util.IPoint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var p = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)");
        var bounds = new IPoint(101, 103);
        var seconds = 100;
        var movedRobots = aoc.inputStr()
                .map(p::matcher)
                .filter(Matcher::find)
                .map(matcher -> {
                    var pos = IPoint.fromStr(matcher.group(1), matcher.group(2));
                    var vel = IPoint.fromStr(matcher.group(3), matcher.group(4));
                    return pos.plus(vel.times(seconds)).map(x -> Math.floorMod(x, bounds.x()), y -> Math.floorMod(y, bounds.y()));
                }).toList();
        int quad1 = 0, quad2 = 0, quad3 = 0, quad4 = 0;
        for (var robot : movedRobots) {
            switch (robot) {
                case IPoint(var x, var y) when x < bounds.x() / 2 && y < bounds.y() / 2 -> quad1++;
                case IPoint(var x, var y) when x > bounds.x() / 2 && y < bounds.y() / 2 -> quad2++;
                case IPoint(var x, var y) when x < bounds.x() / 2 && y > bounds.y() / 2 -> quad3++;
                case IPoint(var x, var y) when x > bounds.x() / 2 && y > bounds.y() / 2 -> quad4++;
                default -> {}
            }
        }
        return quad1 * quad2 * quad3 * quad4;
    }
}