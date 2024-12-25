package year2024.day10;

import aoc.AdventOfCode;
import aoc.Task;
import util.IntMatrix;
import util.IPoint;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Task_A extends Task {

    static {
        new Task_A();
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
            return reachablePeaks().size();
        }

        Set<TrailPos> reachablePeaks() {
            if (val == 9)
                return Set.of(this);
            var peaks = new HashSet<TrailPos>();
            for (var tp : adjacent())
                peaks.addAll(tp.reachablePeaks());
            return peaks;
        }
    }


}