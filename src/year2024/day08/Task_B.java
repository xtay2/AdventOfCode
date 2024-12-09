package year2024.day08;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.*;
import java.util.stream.Collectors;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var matrix = aoc.inputMat();
        var frequencies = matrix.map((x, y, c) -> {
                    if (Character.isDigit(c) || Character.isAlphabetic(c))
                        return new Frequency(x, y, c);
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Frequency::type));
        return frequencies.values()
                .stream()
                .map(freqs -> calcAntinodes(freqs, Math.max(matrix.width(), matrix.height())))
                .flatMap(Collection::stream)
                .distinct()
                .filter(antinode -> matrix.isInBounds(antinode.x(), antinode.y()))
                .count();
    }

    Set<Antinode> calcAntinodes(List<Frequency> frequencies, int mult) {
        var antinodes = new HashSet<Antinode>();
        for (var a : frequencies) {
            for (var b : frequencies) {
                int xDiff = diff(a.x, b.x);
                int yDiff = diff(a.y, b.y);
                for (int m = 1; m < mult; m++) {
                    var ant1 = new Antinode(
                            a.x <= b.x
                                    ? a.x - xDiff * m
                                    : a.x + xDiff * m,
                            a.y <= b.y
                                    ? a.y - yDiff * m
                                    : a.y + yDiff * m
                    );
                    var ant2 = new Antinode(
                            b.x <= a.x
                                    ? b.x - xDiff * m
                                    : b.x + xDiff * m,
                            b.y <= a.y
                                    ? b.y - yDiff * m
                                    : b.y + yDiff * m
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

    record Frequency(int x, int y, char type) {}

    record Antinode(int x, int y) {}
}