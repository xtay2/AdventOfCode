package year2024.day18;

import aoc.AdventOfCode;
import aoc.Task;
import util.CharMatrix;
import util.IPoint;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) throws Exception {
        var bounds = 71;
        var matrix = new CharMatrix(bounds, bounds, '.');
        var bytes = aoc.inputStr()
                .map(line -> IPoint.fromStr(line.split(",")))
                .filter(matrix::isInBounds)
                .toList();
        for (int byteIdx = 0; byteIdx < Integer.MAX_VALUE; byteIdx++) {
            matrix.set(bytes.get(byteIdx), '#');
            var shortestPath = matrix.shortestPath(
                    new IPoint(0, 0),
                    new IPoint(bounds - 1, bounds - 1),
                    (_, _, c) -> c != '#',
                    (x, y, _) -> matrix.set(x, y, 'O')
            );
            if (shortestPath == null)
                return aoc.inputLst().get(byteIdx);
        }
        return "No solution found";
    }
}