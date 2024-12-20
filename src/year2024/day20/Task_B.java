package year2024.day20;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.ArrayList;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var matrix = aoc.inputCharMat();
        var start = matrix.findFirst('S');
        var end = matrix.findFirst('E');
        var path = new ArrayList<>(matrix.shortestPath(start, end, (x, y, v) -> v != '#'));
        var pathLen = path.size();
        var cnt = 0;
        for (var pi1 = 0; pi1 < pathLen - 1; pi1++) {
            for (var pi2 = pi1 + 1; pi2 < pathLen; pi2++) {
                var p1 = path.get(pi1);
                var p2 = path.get(pi2);
                var manDist = p1.manhattanDist(p2);
                if (manDist > 20) continue;
                var pathDist = pi2 - pi1;
                var timeSaved = pathDist - manDist;
                if (timeSaved >= 100)
                    cnt++;
            }
        }
        return cnt;
    }

}