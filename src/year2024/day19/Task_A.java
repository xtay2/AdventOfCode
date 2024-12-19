package year2024.day19;

import aoc.AdventOfCode;
import aoc.Task;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var lines = aoc.inputLst();
        var towels = lines.removeFirst().split(", ");
        return lines.stream()
                .skip(1)
                .filter(line -> matches(line, towels))
                .count();
    }

    private boolean matches(String line, String[] towels) {
        if (line.isEmpty())
            return true;
        for (var towel : towels) {
            if (line.startsWith(towel)) {
                if (matches(line.substring(towel.length()), towels))
                    return true;
            }
        }
        return false;
    }
}