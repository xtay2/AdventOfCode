package year2023.day03;

import aoc.AdventOfCode;
import aoc.Task;
import helper.util.datastructures.tuples.points.IntPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var lines = aoc.inputArr();
        var numbers = new ArrayList<Nr>();
        var symbols = new HashMap<IntPoint, Character>();

        for (int y = 0; y < lines.length; y++) {
            var line = lines[y];
            var nrBuffer = new StringBuilder();
            var nrPosBuffer = new ArrayList<IntPoint>();
            for (int x = 0; x < line.length(); x++) {
                var c = line.charAt(x);
                if (Character.isDigit(c)) {
                    nrBuffer.append(c);
                    nrPosBuffer.add(new IntPoint(x, y));
                    continue;
                } else if (nrBuffer.length() > 0) {
                    var nr = Integer.parseInt(nrBuffer.toString());
                    numbers.add(new Nr(nr, new ArrayList<>(nrPosBuffer)));
                    nrBuffer.setLength(0);
                    nrPosBuffer.clear();
                }
                if (c != '.')
                    symbols.put(new IntPoint(x, y), c);
            }
            if (nrBuffer.length() > 0) {
                var nr = Integer.parseInt(nrBuffer.toString());
                numbers.add(new Nr(nr, new ArrayList<>(nrPosBuffer)));
            }
        }
        var sum = 0;
        for (var entry : symbols.entrySet()) {
            if (entry.getValue() == '*') {
                var pos = entry.getKey();
                var nrsAround = new HashSet<Nr>();
                for (int i = pos.x - 1; i <= pos.x + 1; i++) {
                    for (int j = pos.y - 1; j <= pos.y + 1; j++) {
                        if (i == pos.x && j == pos.y)
                            continue;
                        var point = new IntPoint(i, j);
                        numbers.stream()
                                .filter(nr -> nr.positions.contains(point))
                                .forEach(nrsAround::add);
                    }
                }
                if (nrsAround.size() == 2) {
                    sum += nrsAround.stream().mapToInt(nr -> nr.nr).reduce(1, (a, b) -> a * b);
                }
            }
        }
        return sum;
    }

    record Nr(int nr, List<IntPoint> positions) {}


}