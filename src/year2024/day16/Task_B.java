package year2024.day16;

import aoc.AdventOfCode;
import aoc.Task;
import util.Direction;
import util.IPoint;
import util.Triple;
import util.Tuple;

import java.util.*;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) throws Exception {
        var grid = aoc.inputCharMat();
        var queue = new PriorityQueue<Triple<List<IPoint>, Direction, Integer>>(Comparator.comparing(Triple::c));
        var start = grid.findFirst('S');
        queue.add(new Triple<>(List.of(start), Direction.RIGHT, 0));
        var end = grid.findFirst('E');

        var min = Integer.MAX_VALUE;
        var best = new HashSet<IPoint>();
        var seen = new HashMap<Tuple<IPoint, Direction>, Integer>();
        while (!queue.isEmpty()) {
            var next = queue.poll();
            var p = next.a();
            var d = next.b();
            var s = next.c();

            if (p.getLast().equals(end)) {
                if (s <= min) min = s;
                else return best.size();
                best.addAll(p);
            }
            var pToD = new Tuple<>(p.getLast(), d);
            if (seen.containsKey(pToD) && seen.get(pToD) < s)
                continue;
            seen.put(pToD, s);
            if (grid.get(p.getLast().neighbour(d)) != '#') {
                var ps = new ArrayList<>(p);
                ps.add(p.getLast().neighbour(d));
                queue.add(new Triple<>(ps, d, s + 1));
            }
            queue.add(new Triple<>(p, d.clock(), s + 1000));
            queue.add(new Triple<>(p, d.cntClock(), s + 1000));
        }
        return 0;
    }

}