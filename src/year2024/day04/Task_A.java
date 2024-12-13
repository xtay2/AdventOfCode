package year2024.day04;

import aoc.AdventOfCode;
import aoc.Task;
import util.CharMatrix;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    CharMatrix matrix;
    String search = "XMAS";
    int len = search.length();


    int matches(int x, int y) {
        if (matrix.get(x, y) != search.charAt(0))
            return 0;

        int[] dxs = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dys = {-1, 0, 1, -1, 1, -1, 0, 1};

        int cnt = 0;
        for (int dir = 0; dir < 8; dir++) {
            int charIdx;
            for (charIdx = 1; charIdx < len; charIdx++) {
                int xOffset = x + charIdx * dxs[dir], yOffset = y + charIdx * dys[dir];
                if (!matrix.isInBounds(xOffset, yOffset))
                    break;
                if (matrix.get(xOffset, yOffset) != search.charAt(charIdx))
                    break;
            }

            if (charIdx == len)
                cnt++;
        }

        return cnt;
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        matrix = aoc.inputCharMat();
        return matrix.map((x, y, _) -> matches(x, y))
                .reduce(0, Integer::sum);
    }
}