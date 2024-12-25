package year2024.day08;

import aoc.AdventOfCode;
import aoc.Task;
import util.CharMatrix.CoordValChar;
import util.IPoint;

import java.util.*;
import java.util.stream.Collectors;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var matrix = aoc.inputCharMat();
        var frequencies = matrix.stream()
                .filter(cvc -> Character.isDigit(cvc.val()) || Character.isAlphabetic(cvc.val()))
                .collect(Collectors.groupingBy(CoordValChar::val));
        return frequencies.values()
                .stream()
                .map(this::calcAntinodes)
                .flatMap(Collection::stream)
                .distinct()
                .filter(matrix::isInBounds)
                .count();
    }

    Set<IPoint> calcAntinodes(List<CoordValChar> frequencies) {
        var antinodes = new HashSet<IPoint>();
        for (var a : frequencies) {
            for (var b : frequencies) {
                if (a.equals(b)) continue;
                int xDiff = diff(a.x(), b.x());
                int yDiff = diff(a.y(), b.y());
                var ant1 = new IPoint(
                        a.x() <= b.x()
                                ? a.x() - xDiff
                                : a.x() + xDiff,
                        a.y() <= b.y()
                                ? a.y() - yDiff
                                : a.y() + yDiff
                );
                var ant2 = new IPoint(
                        b.x() <= a.x()
                                ? b.x() - xDiff
                                : b.x() + xDiff,
                        b.y() <= a.y()
                                ? b.y() - yDiff
                                : b.y() + yDiff
                );
                antinodes.add(ant1);
                antinodes.add(ant2);
            }
        }
        return antinodes;
    }

    int diff(int a, int b) {
        return Math.max(a, b) - Math.min(a, b);
    }
}