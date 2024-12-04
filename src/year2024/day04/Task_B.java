package year2024.day04;

import aoc.AdventOfCode;
import aoc.Task;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    char[][] matrix;
    int h, w;
    String search = "MAS";
    int len = search.length();

    boolean inBounds(int x, int y, int dx, int dy, int offset) { // @formatter:off
        return  x + offset * dx >= 0 &&
                x + offset * dx  < h &&
                y + offset * dy >= 0 &&
                y + offset * dy  < w &&
                x                < h &&
                y                < w;
    } // @formatter:on

    int matches(int x, int y, int dx, int dy, int offset) {
        if (!inBounds(x, y, dx, dy, offset))
            return 0;
        var matches = true;
        for (int charIdx = 0; charIdx < len; charIdx++)
            matches &= matrix[x + charIdx * dx][y + charIdx * dy] == search.charAt(charIdx);
        return matches ? 1 : 0;
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        matrix = aoc.inputMat();
        h = matrix.length;
        w = matrix[0].length;
        var offset = len - 1;
        var cnt = 0;
        for (int x = 0; x < h; x++) {
            for (int y = 0; y < w; y++) { // @formatter:off
                cnt += matches(x,          y,           1,  1, offset) * matches(x,          y + offset, 1, -1, offset);
                cnt += matches(x,          y,           1,  1, offset) * matches(x + offset, y,         -1,  1, offset);
                cnt += matches(x + offset, y + offset, -1, -1, offset) * matches(x,          y + offset, 1, -1, offset);
                cnt += matches(x + offset, y + offset, -1, -1, offset) * matches(x + offset, y,         -1,  1, offset);
            } // @formatter:on
        }
        return cnt;
    }
}