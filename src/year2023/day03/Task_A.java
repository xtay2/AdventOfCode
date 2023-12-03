package year2023.day03;

import aoc.AdventOfCode;
import aoc.Task;
import helper.util.datastructures.tuples.points.IntPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Task_A extends Task {

    static {
        new Task_A();
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
        nextNumber:
        for (var entry : numbers) {
            for (var pos : entry.positions) {
                for (int x = pos.x - 1; x <= pos.x + 1; x++) {
                    for (int y = pos.y - 1; y <= pos.y + 1; y++) {
                        if (x == pos.x && y == pos.y)
                            continue;
                        var point = new IntPoint(x, y);
                        if (symbols.containsKey(point)) {
                            sum += entry.nr;
                            continue nextNumber;
                        }
                    }
                }
            }
        }
        return sum;
    }

    record Nr(int nr, List<IntPoint> positions) {}

}