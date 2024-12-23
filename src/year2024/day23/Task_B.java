package year2024.day23;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.*;
import java.util.stream.Collectors;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var neighbours = new HashMap<String, Set<String>>();
        aoc.inputStr().forEach(line -> {
            var parts = line.split("-");
            neighbours.computeIfAbsent(parts[0], _ -> new HashSet<>()).add(parts[1]);
            neighbours.computeIfAbsent(parts[1], _ -> new HashSet<>()).add(parts[0]);
        });
        return findLargestCompleteSubgraph(neighbours, new HashSet<>(), neighbours.keySet(), new HashSet<>())
                .stream()
                .sorted()
                .collect(Collectors.joining(","));
    }

    /// Ripped straight from [Wikipedia](https://en.wikipedia.org/wiki/Bron%E2%80%93Kerbosch_algorithm) :D
    /// ```
    /// algorithm BronKerbosch1(R, P, X) is
    ///     if P and X are both empty then
    ///         report R as a maximal clique
    ///     for each vertex v in P do
    ///         BronKerbosch1(R ⋃ {v}, P ⋂ N(v), X ⋂ N(v))
    ///         P := P \ {v}
    ///         X := X ⋃ {v}
    /// ```
    Set<String> findLargestCompleteSubgraph(Map<String, Set<String>> neighbours, Set<String> R, Set<String> P, Set<String> X) {
        if (P.isEmpty() && X.isEmpty()) {
            return R;
        }
        var max = Collections.<String>emptySet();
        for (var v : P) {
            var res = findLargestCompleteSubgraph(neighbours, with(R, v), intersect(P, neighbours.get(v)), intersect(X, neighbours.get(v)));
            if (res.size() > max.size())
                max = res;
            P = without(P, v);
            X = with(X, v);
        }
        return max;
    }

    Set<String> without(Set<String> a, String b) {
        var res = new HashSet<>(a);
        res.remove(b);
        return res;
    }

    Set<String> with(Set<String> a, String b) {
        var res = new HashSet<>(a);
        res.add(b);
        return res;
    }

    Set<String> intersect(Set<String> a, Set<String> b) {
        var res = new HashSet<>(a);
        res.retainAll(b);
        return res;
    }

}