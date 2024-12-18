package year2024.day18;

import aoc.AdventOfCode;
import aoc.Task;
import util.CharMatrix;
import util.IPoint;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) throws Exception {
        var bounds = 71;
        var bytes = 1024;
        var matrix = new CharMatrix(bounds, bounds, '.');
        aoc.inputStr()
                .limit(bytes)
                .map(IPoint::fromCSV)
                .filter(matrix::isInBounds)
                .forEach(pos -> matrix.set(pos, '#'));
        var shortestPath = matrix.shortestPath(
                0, 0,
                bounds - 1, bounds - 1,
                (x, y, c) -> c != '#',
                (x, y, v) -> matrix.set(x, y, 'O')
        );
        System.out.println(shortestPath);
        System.out.println(matrix);
        return shortestPath.size() - 1;
    }
}