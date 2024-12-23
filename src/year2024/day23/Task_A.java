package year2024.day23;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var neighbours = new HashMap<String, Set<String>>();
        aoc.inputStr().forEach(line -> {
            var parts = line.split("-");
            neighbours.computeIfAbsent(parts[0], _ -> new HashSet<>()).add(parts[1]);
            neighbours.computeIfAbsent(parts[1], _ -> new HashSet<>()).add(parts[0]);
        });
        var connections = new HashSet<Set<String>>();
        for (var n : neighbours.keySet()) {
            for (var xn : neighbours.get(n)) {
                for (var yn : neighbours.get(n)) {
                    if (xn.equals(yn)) continue;
                    if (neighbours.get(xn).contains(yn)) {
                        var group = new HashSet<String>();
                        group.add(xn);
                        group.add(yn);
                        group.add(n);
                        connections.add(group);
                    }
                }
            }
        }
        System.out.println(connections);
        return connections
                .stream()
                .filter(group -> group.stream().anyMatch(s -> s.startsWith("t")))
                .count();
    }
}