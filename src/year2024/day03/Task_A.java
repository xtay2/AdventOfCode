package year2024.day03;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.regex.Pattern;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        Pattern p = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        return p.matcher(aoc.inputTxt())
                .results()
                .mapToInt(res -> Integer.parseInt(res.group(1)) * Integer.parseInt(res.group(2)))
                .sum();
    }

}