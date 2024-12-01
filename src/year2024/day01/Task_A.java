package year2024.day01;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.Arrays;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var tupleList = aoc.inputStr()
                .map(line -> line.split("\s+"))
                .map(tup -> new int[]{Integer.parseInt(tup[0]), Integer.parseInt(tup[1])})
                .toList();
        var leftList = tupleList.stream().mapToInt(tup -> tup[0]).toArray();
        var rightList = tupleList.stream().mapToInt(tup -> tup[1]).toArray();
        Arrays.sort(leftList);
        Arrays.sort(rightList);
        long sum = 0;
        for (int i = 0; i < leftList.length && i < rightList.length; i++)
            sum += distance(leftList[i], rightList[i]);
        return sum;
    }

    private long distance(int a, int b) {
        return Math.abs(a - b);
    }
}