package year2024.day08;

import aoc.AdventOfCode;
import aoc.Task;
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
        var frequencies = matrix.map((x, y, c) -> {
                    if (Character.isDigit(c) || Character.isAlphabetic(c))
                        return new Frequency(x, y, c);
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Frequency::type));
        return frequencies.values()
                .stream()
                .map(this::calcAntinodes)
                .flatMap(Collection::stream)
                .distinct()
                .filter(antinode -> matrix.isInBounds(antinode.x(), antinode.y()))
                .count();
    }

    Set<IPoint> calcAntinodes(List<Frequency> frequencies) {
        var antinodes = new HashSet<IPoint>();
        for (var a : frequencies) {
            for (var b : frequencies) {
                if (a.equals(b)) continue;
                int xDiff = diff(a.x, b.x);
                int yDiff = diff(a.y, b.y);
                var ant1 = new IPoint(
                        a.x <= b.x
                                ? a.x - xDiff
                                : a.x + xDiff,
                        a.y <= b.y
                                ? a.y - yDiff
                                : a.y + yDiff
                );
                var ant2 = new IPoint(
                        b.x <= a.x
                                ? b.x - xDiff
                                : b.x + xDiff,
                        b.y <= a.y
                                ? b.y - yDiff
                                : b.y + yDiff
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

    record Frequency(int x, int y, char type) {}
}