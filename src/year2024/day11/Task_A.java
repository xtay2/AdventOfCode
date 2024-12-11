package year2024.day11;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.Long.*;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var stones = Arrays.stream(aoc.inputTxt().split("\\s+"))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        for (int iteration = 0; iteration < 25; iteration++) {
            for (int i = 0; i < stones.size(); i++) {
                var v = stones.get(i);
                if (v == 0) {
                    stones.set(i, 1L);
                    continue;
                }
                var vStr = String.valueOf(v);
                if (vStr.length() % 2 == 0) {
                    var half = vStr.length() / 2;
                    var a = parseLong(vStr.substring(0, half));
                    var b = parseLong(vStr.substring(half));
                    stones.set(i, a);
                    stones.add(++i, b);
                    continue;
                }
                stones.set(i, v * 2024);
            }
        }
        return stones.size();
    }
}