package year2024.day10;

import aoc.AdventOfCode;
import aoc.Task;
import util.IntMatrix;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var mat = aoc.inputIntMat();
        return mat.map((x, y, v) -> v == 0
                        ? crossSearch(x, y, 0, mat)
                        : null
                ).filter(Objects::nonNull)
                .mapToInt(TrailPos::score)
                .sum();
    }

    TrailPos crossSearch(int x, int y, int currentVal, IntMatrix mat) {
        var coords = mat.get(x, y);
        if (coords instanceof Integer i && i == currentVal) {
            var adjacent = Stream.of(
                    crossSearch(x + 1, y, currentVal + 1, mat),
                    crossSearch(x, y + 1, currentVal + 1, mat),
                    crossSearch(x - 1, y, currentVal + 1, mat),
                    crossSearch(x, y - 1, currentVal + 1, mat)
            ).filter(Objects::nonNull).toList();
            return new TrailPos(x, y, currentVal, adjacent);
        }
        return null;
    }


    record TrailPos(int x, int y, int val, List<TrailPos> adjacent) {

        int score() {
            if (val == 9)
                return 1;
            var sum = 0;
            for (var tp : adjacent())
                sum += tp.score();
            return sum;
        }


        @Override
        public String toString() {
            return "[" + x + ", " + y + "]: " + score();
        }
    }


}