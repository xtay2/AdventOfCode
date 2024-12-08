package year2024.day08;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.*;
import java.util.stream.Collectors;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var matrix = new CharMatrix(aoc.inputMat());
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

    Set<Antinode> calcAntinodes(List<Frequency> frequencies) {
        var antinodes = new HashSet<Antinode>();
        for (var a : frequencies) {
            for (var b : frequencies) {
                if (a.equals(b)) continue;
                int xDiff = diff(a.x, b.x);
                int yDiff = diff(a.y, b.y);
                var ant1 = new Antinode(
                        a.x <= b.x
                                ? a.x - xDiff
                                : a.x + xDiff,
                        a.y <= b.y
                                ? a.y - yDiff
                                : a.y + yDiff
                );
                var ant2 = new Antinode(
                        b.x <= a.x
                                ? b.x - xDiff
                                : b.x + xDiff,
                        b.y <= a.y
                                ? b.y - yDiff
                                : b.y + yDiff
                );
                System.out.println(a + ", " + b + " => " + ant1 + " " + ant2);
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

    record Antinode(int x, int y) {}
}