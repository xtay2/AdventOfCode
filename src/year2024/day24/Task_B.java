package year2024.day24;

import aoc.AdventOfCode;
import aoc.Task;
import util.Quadrupel;

import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static year2024.day24.Task_B.Op.*;

/**
 * @author Dennis Woithe
 */
public class Task_B extends Task {

    static {
        new Task_B();
    }

    static Map<String, Supplier<Gate>> gates = new HashMap<>();

    @Override
    protected Object exec(AdventOfCode aoc) {
        aoc.inputStr()
                .filter(line -> !line.isBlank())
                .forEach(line -> {
                    var matcher = EXP_PATTERN.matcher(line);
                    Quadrupel<String, String, String, Op> x = matcher.find()
                            ? new Quadrupel<>(matcher.group(1), matcher.group(3), matcher.group(4), Op.valueOf(matcher.group(2)))
                            : new Quadrupel<>(null, null, line.split(": ")[0], INPUT);
                    gates.put(x.c(), () -> new Gate(
                            x.a() == null ? null : gates.get(x.a()).get(),
                            x.b() == null ? null : gates.get(x.b()).get(),
                            x.d(),
                            x.c()
                    ));
                });
        var fails = new ArrayList<String>();
        for (int z = 0; z < 45; z++) {
            var gate = gates.get("z" + (z < 10 ? "0" : "") + z).get();
            if (findErrorInRippleCarryAdder(gate, z) instanceof Gate(_, _, _, var c))
                fails.add(c);
        }
        return fails.stream().sorted().collect(Collectors.joining(","));
    }

    /**
     * Checks, if the passed gate and all preceding gates model a ripple carry adder.
     * Reports the first component on the top layer that doesn't fit this structure.
     * <p>
     * The model is as follows:
     * <pre><code>
     * if N = 0:
     *    z0 = xor(y0, x0)
     *
     * if N = 1:
     *    z1 = xor(and(x0, y0), xor(y1, x1))
     *
     * if N > 1:
     *    zN = xor(
     *       or(
     *          and(mkg, sbv),
     *          and(xN-1, yN-1)
     *       ),
     *       xor(yN, xN)
     *    )
     * </code></pre>
     */
    Gate findErrorInRippleCarryAdder(Gate z, int N) {
        if (z.op() == XOR) {
            if (N == 0) {
                return z.both(INPUT);
            } else if (N == 1) {
                return z.either(AND, XOR);
            } else {
                if (z.either(OR, XOR) instanceof Gate fail)
                    return fail;
                if (z.a().op() == OR)
                    return z.a().both(AND);
                if (z.b().op() == OR)
                    return z.b().both(AND);
            }
            return null;
        } else
            return z;
    }

    Pattern EXP_PATTERN = Pattern.compile("(\\w+) (AND|OR|XOR) (\\w+) -> (\\w+)");

    enum Op {
        INPUT, AND, OR, XOR
    }

    record Gate(Gate a, Gate b, Op op, String c) {

        /**
         * Both sub-gates a and b should have either type ca and cb or cb and ca.
         * Reports the first sub-gate which doesn't match this pattern.
         */
        Gate either(Op ca, Op cb) {
            if (a().op() == (ca)) {
                if (b().op() != (cb)) return b();
            } else if (a().op() == (cb)) {
                if (b().op() != (ca)) return b();
            } else if (b().op() == (ca)) {
                return a();
            } else if (b().op() == (cb))
                return a();
            else throw new IllegalStateException(a().c() + " and " + b().c() + " are weird");
            return null;
        }

        /**
         * Both sub-gates a and b should have type op.
         * Reports the first sub-gate which doesn't match this pattern.
         */
        Gate both(Op op) {
            if (a().op() != op)
                return a();
            if (b().op() != op)
                return b();
            return null;
        }
    }

}