package year2024.day16;

import aoc.AdventOfCode;
import aoc.Task;
import util.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) throws Exception {
        var grid = aoc.inputCharMat();
        var queue = new PriorityQueue<Triple<IPoint, Direction, Integer>>(Comparator.comparing(Triple::c));
        var start = grid.stream().filter((cvc) -> cvc.val() == 'S').findFirst().map(CharMatrix.CoordValChar::pos).orElseThrow();
        queue.add(new Triple<>(start, Direction.RIGHT, 0));
        var end = grid.stream().filter((cvc) -> cvc.val() == 'E').findFirst().map(CharMatrix.CoordValChar::pos).orElseThrow();

        var seen = new HashMap<Tuple<IPoint, Direction>, Integer>();
        while (!queue.isEmpty()) {
            var next = queue.poll();
            var p = next.a();
            var d = next.b();
            var s = next.c();

            if (p.equals(end))
                return s;
            var pToD = new Tuple<>(p, d);
            if (seen.containsKey(pToD) && seen.get(pToD) < s)
                continue;
            seen.put(pToD, s);
            if (grid.get(p.neighbour(d)) != '#')
                queue.add(new Triple<>(p.neighbour(d), d, s + 1));
            queue.add(new Triple<>(p, d.clock(), s + 1000));
            queue.add(new Triple<>(p, d.cntClock(), s + 1000));
        }
        return 0;
    }

}