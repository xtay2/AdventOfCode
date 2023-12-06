package year2022.day01;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.Arrays;

import static java.util.Arrays.stream;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        return stream(aoc.inputTxt().split("\n\n"))
                .mapToInt(group -> Arrays.stream(group.split("\n"))
                        .mapToInt(Integer::parseInt)
                        .sum())
                .max()
                .orElseThrow();
    }
}