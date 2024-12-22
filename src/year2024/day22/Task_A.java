package year2024.day22;

import aoc.AdventOfCode;
import aoc.Task;


/**
 * @author Dennis Woithe
 */
public class Task_A extends Task {

    static {
        new Task_A();
    }

    static long PRUNE_CONST = 16777216;

    @Override
    protected Object exec(AdventOfCode aoc) {
        return aoc.inputStr()
                .parallel()
                .mapToLong(Long::parseLong)
                .map(l -> {
                    for (int i = 0; i < 2000; i++)
                        l = nextSecretFrom(l);
                    return l;
                }).sum();
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