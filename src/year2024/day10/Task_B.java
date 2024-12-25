package year2024.day10;

import aoc.AdventOfCode;
import aoc.Task;
import util.IntMatrix;
import util.IPoint;

import java.util.List;
import java.util.Objects;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var mat = aoc.inputIntMat();
        return mat.map((x, y, v) -> v == 0
                        ? crossSearch(new IPoint(x, y), 0, mat)
                        : null
                ).filter(Objects::nonNull)
                .mapToInt(TrailPos::score)
                .sum();
    }

    TrailPos crossSearch(IPoint pos, int currentVal, IntMatrix mat) {
        var coords = mat.get(pos);
        if (coords instanceof Integer i && i == currentVal) {
            var adjacent = mat.neighbours(pos)
                    .map(p -> crossSearch(p, currentVal + 1, mat))
                    .filter(Objects::nonNull)
                    .toList();
            return new TrailPos(pos, currentVal, adjacent);
        }
        return null;
    }


    record TrailPos(IPoint pos, int val, List<TrailPos> adjacent) {

        int score() {
            if (val == 9)
                return 1;
            var sum = 0;
            for (var tp : adjacent())
                sum += tp.score();
            return sum;
        }

    }


}