package year2023.day01;

import aoc.AdventOfCode;
import aoc.Task;
import helper.base.text.StringHelper;

import java.util.List;

public class Task_B extends Task {

    static final List<String> NUMBERS = List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
    static final String NUMBERS_REGEX = String.join("|", NUMBERS) + "|\\d";

    static {
        new Task_B();
    }

    private static String strToNrStr(String str) {
        return NUMBERS.contains(str) ? String.valueOf(NUMBERS.indexOf(str) + 1) : str;
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        return aoc.inputStr()
                .map(line -> {
                    var all = StringHelper.findAll(NUMBERS_REGEX, line);
                    return strToNrStr(all.get(0)) + strToNrStr(all.get(all.size() - 1));
                })
                .mapToLong(Long::parseLong)
                .sum();
    }
}