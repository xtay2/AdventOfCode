package year2024.day22;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.*;

import static java.util.Collections.*;


/**
 * @author Dennis Woithe
 */
public class Task_B extends Task {

    static {
        new Task_B();
    }

    static long PRUNE_CONST = 16777216;

    @Override
    protected Object exec(AdventOfCode aoc) {
        var changes = new HashMap<List<Integer>, Integer>();
        aoc.inputStr()
                .mapToLong(Long::parseLong)
                .forEach(l -> {
                    var changesSeen = new HashSet<List<Integer>>();
                    var change = new ArrayList<Integer>();
                    for (int i = 0; i < 2000; i++) {
                        long original = l;
                        l = nextSecretFrom(l);
                        int price = (int) l % 10;
                        change.add((int) (price - original % 10));
                        if (change.size() > 4) {
                            change.removeFirst();
                            var r = new ArrayList<>(change);
                            if (!changesSeen.contains(r)) {
                                if (!changes.containsKey(r))
                                    changes.put(r, 0);
                                changes.put(r, changes.get(r) + price);
                                changesSeen.add(r);
                            }
                        }
                    }
                });
        return max(changes.values());
    }

    long nextSecretFrom(long l) {
        l ^= l * 64;
        l %= PRUNE_CONST;
        l ^= l / 32;
        l %= PRUNE_CONST;
        l ^= l * 2048;
        l %= PRUNE_CONST;
        return l;
    }
}