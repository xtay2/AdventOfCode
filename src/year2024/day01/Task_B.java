package year2024.day01;

import aoc.AdventOfCode;
import aoc.Task;
import util.IPoint;

import java.util.HashMap;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var tupleList = aoc.inputStr()
                .map(line -> line.split(" +"))
                .map(IPoint::fromStr)
                .toList();
        var leftList = tupleList.stream().mapToInt(IPoint::x).sorted().toArray();
        var rightList = tupleList.stream().mapToInt(IPoint::y).sorted().toArray();
        var occ = new HashMap<Integer, Integer>(); // <Nr, Count>
        for (int j : rightList)
            occ.compute(j, (_, v) -> v == null ? 1 : (v + 1));
        var simScore = 0;
        for (int j : leftList)
            simScore += j * occ.getOrDefault(j, 0);
        return simScore;
    }
}