package year2022.day13;

import aoc.AdventOfCode;
import aoc.Task;
import helper.util.datastructures.tuples.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static helper.base.StringHelper.occ;
import static java.lang.Math.min;
import static java.lang.String.join;
import static java.util.Arrays.stream;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        String dividerA = "[[2]]", dividerB = "[[6]]";
        var list = stream((aoc.inputTxt() + join("\n", dividerA, dividerB)).split("\n\n?"))
                .map(this::str2Comp)
                .sorted((a, b) -> compare(new Tuple<>(a.val, b.val)))
                .toList();
        return (list.indexOf(eval(dividerA)) + 1) * (list.indexOf(eval(dividerB)) + 1);
    }

    private int compare(Tuple<?, ?> tuple) {
        if (tuple.x instanceof List<?> l1 && tuple.y instanceof List<?> l2) {
            for (int i = 0; i < min(l1.size(), l2.size()); i++) {
                var current = compare(new Tuple<>(((Composite<?>) l1.get(i)).val(), ((Composite<?>) l2.get(i)).val()));
                if (current != 0)
                    return current;
            }
            return Integer.compare(l1.size(), l2.size());
        }
        if (tuple.x instanceof Integer i1 && tuple.y instanceof Integer i2)
            return Integer.compare(i1, i2);
        if (tuple.x instanceof Integer i1)
            return compare(new Tuple<>(List.of(new Composite<>(i1)), tuple.y));
        if (tuple.y instanceof Integer i2)
            return compare(new Tuple<>(tuple.x, List.of(new Composite<>(i2))));
        throw new AssertionError("Unknown Types: " + tuple);
    }

    private Composite<?> str2Comp(String v) {
        var res = new ArrayList<Composite<?>>();
        var sb = new StringBuilder();
        v = v.substring(1, v.length() - 1);
        for (char c : v.toCharArray()) {
            var current = sb.toString();
            if (c == ',' && occ('[', current) == occ(']', current)) {
                res.add(eval(current));
                sb = new StringBuilder();
            } else
                sb.append(c);
        }
        if (!sb.isEmpty())
            res.add(eval(sb.toString()));
        return new Composite<>(res);
    }

    private Composite<?> eval(String current) {
        return current.matches("\\d+")
                ? new Composite<>(Integer.parseInt(current))
                : str2Comp(current);
    }

    private record Composite<C>(C val) {

        @Override
        public boolean equals(Object obj) {
            return this == obj || obj instanceof Composite<?> c && Objects.equals(val, c.val);
        }

        @Override
        public String toString() {
            return Objects.toString(val);
        }
    }
}