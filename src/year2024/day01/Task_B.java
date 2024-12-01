package year2024.day01;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.HashMap;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var tupleList = aoc.inputStr()
                .map(line -> line.split(" +"))
                .map(tup -> new int[]{Integer.parseInt(tup[0]), Integer.parseInt(tup[1])})
                .toList();
        var leftList = tupleList.stream().mapToInt(tup -> tup[0]).toArray();
        var rightList = tupleList.stream().mapToInt(tup -> tup[1]).toArray();
        var occ = new HashMap<Integer, Integer>(); // <Nr, Count>
        for (int j : rightList)
            occ.compute(j, (k, v) -> v == null ? 1 : (v + 1));
        var simScore = 0;
        for (int j : leftList)
            simScore += j * occ.getOrDefault(j, 0);
        return simScore;
    }
}