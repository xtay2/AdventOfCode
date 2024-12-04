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

    boolean inBounds(int x, int y) { // @formatter:off
        int h = matrix.length, w = matrix[0].length;
        return  x <  h &&
                x >= 0 &&
                y <  w &&
                y >= 0;
    } // @formatter:on

    int matches(int x, int y, int dx, int dy) {
        var matches = true;
        for (int charIdx = 0; charIdx < len; charIdx++) {
            int xOffset = x + charIdx * dx, yOffset = y + charIdx * dy;
            if (!inBounds(xOffset, yOffset))
                return 0;
            matches &= matrix[xOffset][yOffset] == search.charAt(charIdx);
        }
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
                cnt += matches(x,          y,           1,  1) * matches(x,          y + offset, 1, -1);
                cnt += matches(x,          y,           1,  1) * matches(x + offset, y,         -1,  1);
                cnt += matches(x + offset, y + offset, -1, -1) * matches(x,          y + offset, 1, -1);
                cnt += matches(x + offset, y + offset, -1, -1) * matches(x + offset, y,         -1,  1);
            } // @formatter:on
        }
        return cnt;
    }
}