package year2024.day19;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.HashMap;

public class Task_B extends Task {

    private static final HashMap<String, Long> CACHE = new HashMap<>();

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var lines = aoc.inputLst();
        var towels = lines.removeFirst().split(", ");
        return lines.stream()
                .skip(1)
                .parallel()
                .mapToLong(line -> matches(line, towels))
                .sum();
    }

    private long matches(String line, String[] towels) {
        if (CACHE.containsKey(line))
            return CACHE.get(line);
        if (line.isEmpty())
            return 1;
        var cnt = 0L;
        for (var towel : towels) {
            if (line.startsWith(towel)) {
                cnt += matches(line.substring(towel.length()), towels);
            }
        }
        CACHE.put(line, cnt);
        return cnt;
    }
}