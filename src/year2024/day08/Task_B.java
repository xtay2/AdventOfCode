package year2024.day08;

import aoc.AdventOfCode;
import aoc.Task;
import util.CharMatrix.CoordValChar;
import util.IPoint;

import java.util.*;
import java.util.stream.Collectors;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var matrix = aoc.inputCharMat();
        var frequencies = matrix.stream()
                .filter(cvc -> Character.isDigit(cvc.val()) || Character.isAlphabetic(cvc.val()))
                .collect(Collectors.groupingBy(CoordValChar::val));
        return frequencies.values()
                .stream()
                .map(freqs -> calcAntinodes(freqs, Math.max(matrix.width(), matrix.height())))
                .flatMap(Collection::stream)
                .distinct()
                .filter(matrix::isInBounds)
                .count();
    }

    Set<IPoint> calcAntinodes(List<CoordValChar> frequencies, int mult) {
        var antinodes = new HashSet<IPoint>();
        for (var a : frequencies) {
            for (var b : frequencies) {
                int xDiff = diff(a.x(), b.x());
                int yDiff = diff(a.y(), b.y());
                for (int m = 1; m < mult; m++) {
                    var ant1 = new IPoint(
                            a.x() <= b.x()
                                    ? a.x() - xDiff * m
                                    : a.x() + xDiff * m,
                            a.y() <= b.y()
                                    ? a.y() - yDiff * m
                                    : a.y() + yDiff * m
                    );
                    var ant2 = new IPoint(
                            b.x() <= a.x()
                                    ? b.x() - xDiff * m
                                    : b.x() + xDiff * m,
                            b.y() <= a.y()
                                    ? b.y() - yDiff * m
                                    : b.y() + yDiff * m
                    );
                    antinodes.add(ant1);
                    antinodes.add(ant2);
                }
            }
        }
        return antinodes;
    }

    int diff(int a, int b) {
        return Math.max(a, b) - Math.min(a, b);
    }


}