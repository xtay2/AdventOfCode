package year2024.day12;

import aoc.AdventOfCode;
import aoc.Task;
import util.Point;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        Map<Character, Set<Point>> points = new HashMap<>();
        var mat = aoc.inputCharMat();
        return mat.map((x, y, v) -> {
                    var visited = points.computeIfAbsent(v, _ -> new HashSet<>());
                    var newRegion = new Region(v, new HashSet<>());
                    visited.addAll(mat.dfs(x, y,
                            (_, _, val) -> val == v,
                            (x1, y1, _) -> newRegion.points().add(new Point(x1, y1))
                    ));
                    return newRegion;
                })
                .distinct()
                .mapToLong(Region::price)
                .sum();
    }


    record Region(char c, Set<Point> points) {

        int perimeter() {
            var sum = 0;
            for (var point : points)
                sum += (4 - neighbours(point));
            return sum;
        }

        int neighbours(Point p) {
            return (int) p.neighbours()
                    .filter(points::contains)
                    .count();
        }

        int area() {
            return points.size();
        }

        int price() {
            return area() * perimeter();
        }

    }
}