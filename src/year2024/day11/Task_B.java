package year2024.day11;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Long.parseLong;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        Map<Long, Long> stones = Arrays.stream(aoc.inputTxt().split("\\s+"))
                .map(Long::parseLong)
                .collect(Collectors.toMap(
                        k -> k,
                        _ -> 1L
                ));

        var temp = new HashMap<Long, Long>();
        for (var iteration = 0; iteration < 75; iteration++) {
            temp = new HashMap<>();
            for (var entry : stones.entrySet()) {
                var v = entry.getKey();
                var cnt = entry.getValue();
                if (v == 0) {
                    temp.put(1L, temp.getOrDefault(1L, 0L) + cnt);
                    continue;
                }
                var vStr = String.valueOf(v);
                if (vStr.length() % 2 == 0) {
                    var half = vStr.length() / 2;
                    var a = parseLong(vStr.substring(0, half));
                    var b = parseLong(vStr.substring(half));
                    temp.put(a, (temp.getOrDefault(a, 0L)) + cnt);
                    temp.put(b, (temp.getOrDefault(b, 0L)) + cnt);
                    continue;
                }
                temp.put(v * 2024, temp.getOrDefault(v * 2024, 0L) + cnt);
            }
            stones = temp;
        }
        return stones.values().stream().mapToLong(Long::longValue).sum();
    }

}