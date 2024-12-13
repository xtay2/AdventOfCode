package year2024.day13;

import aoc.AdventOfCode;
import aoc.Task;
import util.IPoint;

import java.util.Arrays;
import java.util.regex.Pattern;

public class Task_A extends Task {

    static final Pattern LINE_PATTERN = Pattern.compile(".*?: X.(\\d+), Y.(\\d+)");

    static {
        new Task_A();
    }

    static IPoint parsePoint(String line) {
        var matcher = LINE_PATTERN.matcher(line);
        if (!matcher.find()) throw new AssertionError("Invalid line: " + line);
        return new IPoint(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        return Arrays.stream(aoc.inputTxt().split("\\R\\R"))
                .parallel()
                .map((block) -> {
                    var lines = block.lines().toList();
                    assert lines.size() == 3;
                    return new Machine(parsePoint(lines.get(0)), parsePoint(lines.get(1)), parsePoint(lines.get(2)));
                })
                .mapToLong(Machine::tokenCost)
                .sum();
    }


    record Machine(IPoint buttonA, IPoint buttonB, IPoint prizePos) {
        public int tokenCost() {
            int numerator = prizePos.x() * buttonA.y() - prizePos.y() * buttonA.x();
            int b = numerator / (buttonB.x() * buttonA.y() - buttonB.y() * buttonA.x());
            int remX = prizePos.x() - b * buttonB.x();
            int l = buttonA.x() == 0 ? prizePos.y() : remX;
            int r = buttonA.x() == 0 ? buttonA.y() : buttonA.x();
            if (l % r != 0) return 0;
            int a = l / r;
            return (a * buttonA.y() + b * buttonB.y() == prizePos.y()) ? 3 * a + b : 0;
        }
    }


}