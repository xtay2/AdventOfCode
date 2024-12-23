package year2023.day01;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var pat = Pattern.compile("\\d");
        return aoc.inputStr()
                .map(line -> {
                    var all = pat.matcher(line).results().map(MatchResult::group).toList();
                    return all.get(0) + all.get(all.size() - 1);
                })
                .mapToInt(Integer::parseInt)
                .sum();
    }
}