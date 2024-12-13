package year2024.day10;

import aoc.AdventOfCode;
import aoc.Task;
import util.IntMatrix;
import util.Point;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.HashSet;
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
                        ? crossSearch(new Point(x, y), 0, mat)
                        : null
                ).filter(Objects::nonNull)
                .mapToInt(TrailPos::score)
                .sum();
    }

    TrailPos crossSearch(Point pos, int currentVal, IntMatrix mat) {
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


    record TrailPos(Point pos, int val, List<TrailPos> adjacent) {

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
            return pos + ": " + score();
        }
    }


}