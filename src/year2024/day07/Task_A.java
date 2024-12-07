package year2024.day07;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        return aoc.inputStr()
                .map(line -> {
                    var split = line.split(": ");
                    return new Eq(
                            Long.parseLong(split[0]),
                            Arrays.stream(split[1].split("\\s")).map(Long::parseLong).collect(Collectors.toList())
                    );
                })
                .filter(this::couldBeTrue)
                .mapToLong(Eq::res)
                .sum();
    }

    boolean couldBeTrue(Eq eq) {
        return results(eq.operands()).contains(eq.res);
    }

    List<Long> results(List<Long> operands) {
        if (operands.size() == 1)
            return operands;
        var res = new ArrayList<Long>();
        var last = operands.removeLast();
        for (var calc : results(operands)) {
            res.add(calc + last);
            res.add(calc * last);
        }
        return res;
    }

    record Eq(long res, List<Long> operands) {}
}