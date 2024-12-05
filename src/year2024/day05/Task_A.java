package year2024.day05;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    Set<Rule> rules;

    @Override
    protected Object exec(AdventOfCode aoc) {
        var input = aoc.inputArr();
        rules = parseRules(input);
        List<List<Integer>> printings = parsePrintings(input);
        return printings
                .stream()
                .filter(printing -> {
                    var sortedCpy = new ArrayList<>(printing);
                    sortedCpy.sort((o1, o2) -> rules.contains(new Rule(o1, o2)) ? -1 : 1);
                    return sortedCpy.equals(printing);
                })
                .mapToInt(printing -> printing.get(printing.size() / 2))
                .sum();
    }

    Set<Rule> parseRules(String[] input) {
        return Arrays.stream(input)
                .takeWhile(line -> !line.isBlank())
                .map(line -> line.split("\\|"))
                .map(split -> new Rule(split[0], split[1]))
                .collect(Collectors.toSet());
    }

    List<List<Integer>> parsePrintings(String[] input) {
        return Arrays.stream(input)
                .dropWhile(line -> !line.isBlank())
                .skip(1)
                .map(line -> line.split(","))
                .map(split -> Arrays.stream(split).map(Integer::parseInt).collect(Collectors.toList()))
                .toList();
    }

    record Rule(int before, int after) {

        Rule(String before, String after) {
            this(Integer.parseInt(before), Integer.parseInt(after));
        }

    }

}