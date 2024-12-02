package year2024.day02;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.abs;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    public Object exec(AdventOfCode aoc) {
        return aoc.inputStr()
                .map(line -> line.split(" "))
                .map(line -> Arrays.stream(line).map(Integer::parseInt).toList())
                .filter(line -> isSafe(line) || take1(line).anyMatch(this::isSafe))
                .count();
    }

    private Stream<List<Integer>> take1(List<Integer> line) {
        return IntStream.range(0, line.size())
                .mapToObj(rmIdx -> {
                    var cpy = new ArrayList<>(line);
                    cpy.remove(rmIdx);
                    return cpy;
                });
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