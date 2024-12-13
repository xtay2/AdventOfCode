package year2024.day13;

import aoc.AdventOfCode;
import aoc.Task;
import util.LPoint;

import java.util.Arrays;
import java.util.regex.Pattern;

import static java.lang.Long.parseLong;

public class Task_B extends Task {

    static final Pattern LINE_PATTERN = Pattern.compile(".*?: X.(\\d+), Y.(\\d+)");

    static {
        new Task_B();
    }

    static LPoint parsePoint(String line) {
        var matcher = LINE_PATTERN.matcher(line);
        if (!matcher.find()) throw new AssertionError("Invalid line: " + line);
        return new LPoint(parseLong(matcher.group(1)), parseLong(matcher.group(2)));
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        return Arrays.stream(aoc.inputTxt().split("\\R\\R"))
                .parallel()
                .map((block) -> {
                    var lines = block.lines().toList();
                    assert lines.size() == 3;
                    return new Machine(
                            parsePoint(lines.get(0)),
                            parsePoint(lines.get(1)),
                            parsePoint(lines.get(2))
                    );
                })
                .map(machine -> new Machine(
                        machine.buttonA(),
                        machine.buttonB(),
                        new LPoint(machine.prizePos().x() + 10_000_000_000_000L, machine.prizePos().y() + 10_000_000_000_000L)
                ))
                .mapToLong(Machine::tokenCost)
                .sum();
    }


    record Machine(LPoint buttonA, LPoint buttonB, LPoint prizePos) {
        public long tokenCost() {
            long numerator = prizePos.x() * buttonA.y() - prizePos.y() * buttonA.x();
            long b = numerator / (buttonB.x() * buttonA.y() - buttonB.y() * buttonA.x());
            long remX = prizePos.x() - b * buttonB.x();
            long l = buttonA.x() == 0
                    ? prizePos.y() : remX;
            long r = buttonA.x() == 0
                    ? buttonA.y() : buttonA.x();
            if (l % r != 0) return 0;
            long a = l / r;
            return (a * buttonA.y() + b * buttonB.y() == prizePos.y()) ? 3 * a + b : 0;
        }
    }


}