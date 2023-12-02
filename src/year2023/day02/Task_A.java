package year2023.day02;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        return aoc.inputStr()
                .map(line -> {
                    var splitColon = line.split(":");
                    var id = splitColon[0].substring("Game ".length());
                    var splitSemi = splitColon[1].split(";");
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
                    return colors.getOrDefault("red", 0) <= 12 &&
                            colors.getOrDefault("green", 0) <= 13 &&
                            colors.getOrDefault("blue", 0) <= 14
                            ? id : null;
                })
                .filter(Objects::nonNull)
                .mapToInt(Integer::parseInt)
                .sum();

    }
}