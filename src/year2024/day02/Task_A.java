package year2024.day02;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        return aoc.inputStr()
                .map(line -> line.split(" "))
                .map(line -> Arrays.stream(line).map(Integer::parseInt).toList())
                .filter(this::isSafe)
                .count();
    }

    private boolean isSafe(List<Integer> levels) {
        var isIncreasing = true;
        var isDecreasing = true;

        for (int i = 1; i < levels.size(); i++) {
            int diff = levels.get(i) - levels.get(i - 1);
            if (abs(diff) < 1 || abs(diff) > 3)
                return false;

            if (diff > 0)
                isDecreasing = false;
            else if (diff < 0)
                isIncreasing = false;

        }

        return isIncreasing || isDecreasing;
    }
}