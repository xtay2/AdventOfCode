package year2023.day02;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.Arrays;
import java.util.HashMap;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        return aoc.inputStr()
                .mapToInt(line -> {
                    var splitSemi = line.substring(line.indexOf(':') + 1).split(";");
                    var colors = Arrays.stream(splitSemi)
                            .flatMap(gameLine -> Arrays.stream(gameLine.split(",")))
                            .reduce(new HashMap<String, Integer>(), (map, gameLine) -> {
                                var splitSpace = gameLine.trim().split(" ");
                                var key = splitSpace[1];
                                map.put(key, Math.max(map.getOrDefault(key, 0), Integer.parseInt(splitSpace[0])));
                                return map;
                            }, (map1, map2) -> {
                                map1.putAll(map2);
                                return map1;
                            });
                    return colors.getOrDefault("red", 1) *
                            colors.getOrDefault("green", 1) *
                            colors.getOrDefault("blue", 1);
                }).sum();
    }
}