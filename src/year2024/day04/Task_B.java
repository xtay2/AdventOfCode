package year2024.day04;

import aoc.AdventOfCode;
import aoc.Task;
import util.CharMatrix;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    CharMatrix matrix;
    String search = "MAS";
    int len = search.length();

    int matches(int x, int y, int dx, int dy) {
        var matches = true;
        for (int charIdx = 0; charIdx < len; charIdx++) {
            int xOffset = x + charIdx * dx, yOffset = y + charIdx * dy;
            if (!matrix.isInBounds(xOffset, yOffset))
                return 0;
            matches &= matrix.get(xOffset, yOffset) == search.charAt(charIdx);
        }
        return matches ? 1 : 0;
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        matrix = aoc.inputCharMat();
        var offset = len - 1;
        return matrix.map((x, y, _) ->  // @formatter:off
                  matches(x,          y,           1,  1) * matches(x,          y + offset, 1, -1)
                + matches(x,          y,           1,  1) * matches(x + offset, y,         -1,  1)
                + matches(x + offset, y + offset, -1, -1) * matches(x,          y + offset, 1, -1)
                + matches(x + offset, y + offset, -1, -1) * matches(x + offset, y,         -1,  1)
                // @formatter:on
        ).reduce(0, Integer::sum);
    }
}