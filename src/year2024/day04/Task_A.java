package year2024.day04;

import aoc.AdventOfCode;
import aoc.Task;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    char[][] matrix;
    int h, w;
    String search = "XMAS";
    int len = search.length();

    boolean inBounds(int x, int y) { // @formatter:off
        int h = matrix.length, w = matrix[0].length;
        return  x <  h &&
                x >= 0 &&
                y <  w &&
                y >= 0;
    } // @formatter:on

    int matches(int x, int y) {
        if (matrix[x][y] != search.charAt(0))
            return 0;

        int[] dxs = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dys = {-1, 0, 1, -1, 1, -1, 0, 1};

        int cnt = 0;
        for (int dir = 0; dir < 8; dir++) {
            int charIdx;
            for (charIdx = 1; charIdx < len; charIdx++) {
                int xOffset = x + charIdx * dxs[dir], yOffset = y + charIdx * dys[dir];
                if (!inBounds(xOffset, yOffset))
                    break;
                if (matrix[xOffset][yOffset] != search.charAt(charIdx))
                    break;
            }

            if (charIdx == len)
                cnt++;
        }

        return cnt;
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        matrix = aoc.inputMat();
        h = matrix.length;
        w = matrix[0].length;
        var cnt = 0;
        for (int x = 0; x < h; x++) {
            for (int y = 0; y < w; y++)
                cnt += matches(x, y);
        }
        return cnt;
    }
}