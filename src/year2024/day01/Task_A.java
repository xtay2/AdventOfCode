package year2024.day01;

import aoc.AdventOfCode;
import aoc.Task;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var tupleList = aoc.inputStr()
                .map(line -> line.split(" +"))
                .map(tup -> new int[]{Integer.parseInt(tup[0]), Integer.parseInt(tup[1])})
                .toList();
        var leftList = tupleList.stream().mapToInt(tup -> tup[0]).sorted().toArray();
        var rightList = tupleList.stream().mapToInt(tup -> tup[1]).sorted().toArray();
        long sum = 0;
        for (int i = 0; i < leftList.length && i < rightList.length; i++)
            sum += Math.abs(leftList[i] - rightList[i]);
        return sum;
    }
}