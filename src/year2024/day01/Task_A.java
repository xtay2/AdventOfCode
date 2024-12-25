package year2024.day01;

import aoc.AdventOfCode;
import aoc.Task;
import util.IPoint;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var tupleList = aoc.inputStr()
                .map(line -> line.split(" +"))
                .map(IPoint::fromStr)
                .toList();
        var leftList = tupleList.stream().mapToInt(IPoint::x).sorted().toArray();
        var rightList = tupleList.stream().mapToInt(IPoint::y).sorted().toArray();
        long sum = 0;
        for (int i = 0; i < leftList.length && i < rightList.length; i++)
            sum += Math.abs(leftList[i] - rightList[i]);
        return sum;
    }
}