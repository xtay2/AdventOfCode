package year2023.day01;

import aoc.AdventOfCode;
import aoc.Task;

import static helper.base.text.StringHelper.findAll;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        return aoc.inputStr()
                .map(line -> {
                    var all = findAll("\\d", line);
                    System.out.println(all + " for " + line);
                    return all.get(0) + all.get(all.size() - 1);
                })
                .mapToInt(Integer::parseInt)
                .sum();
    }
}