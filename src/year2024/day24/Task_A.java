package year2024.day24;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Dennis Woithe
 */
public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var configuration = aoc.inputStr()
                .takeWhile(line -> !line.isBlank())
                .collect(Collectors.toMap(
                        line -> line.split(": ")[0],
                        line -> Integer.parseInt(line.split(": ")[1])
                ));
        var inputLst = aoc.inputLst();
        var inputQueue = new ArrayDeque<>(inputLst.subList(configuration.size() + 1, inputLst.size()));
        while (!inputQueue.isEmpty()) {
            var nextLine = inputQueue.pop();
            if (find(nextLine, configuration) == null)
                inputQueue.add(nextLine);
        }
        long res = 0L;
        for (int i = 0; i < 64; i++) {
            var key = (i < 10 ? "z0" : "z") + i;
            long bit = configuration.getOrDefault(key, 0);
            res |= bit << i;
        }
        return res;
    }

    Pattern EXP_PATTERN = Pattern.compile("(\\w+) (AND|OR|XOR) (\\w+) -> (\\w+)");

    String find(String line, Map<String, Integer> configuration) {
        var matcher = EXP_PATTERN.matcher(line);
        matcher.find();
        var a = configuration.get(matcher.group(1));
        var b = configuration.get(matcher.group(3));
        if (a == null || b == null)
            return null;
        var op = matcher.group(2);
        var cStr = matcher.group(4);
        var res = compute(a, b, op);
        configuration.put(cStr, res);
        return cStr;
    }

    int compute(int a, int b, String op) {
        return switch (op) {
            case "AND" -> a & b;
            case "OR" -> a | b;
            case "XOR" -> a ^ b;
            default -> throw new IllegalStateException("Unexpected value: " + op);
        };
    }

}