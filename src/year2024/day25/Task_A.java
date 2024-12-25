package year2024.day25;

import aoc.AdventOfCode;
import aoc.Task;
import util.CharMatrix;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Dennis Woithe
 */
public class Task_A extends Task {

    static {
        new Task_A();
    }

    static final int MAX_LOCK_HEIGHT = 7;

    @Override
    protected Object exec(AdventOfCode aoc) {
        var locks = new ArrayList<Lock>();
        var keys = new ArrayList<Key>();
        Arrays.stream(aoc.inputTxt().split("\\R\\R"))
                .map(section -> {
                    var lines = section.split("\\R");
                    char[][] c = new char[lines.length][];
                    for (var i = 0; i < lines.length; i++)
                        c[i] = lines[i].toCharArray();
                    return new CharMatrix(c);
                })
                .forEach(schematic -> {
                    if (schematic.get(0, 0) == '#')
                        locks.add(new Lock(schematic));
                    else
                        keys.add(new Key(schematic));
                });
        var fits = 0;
        for (var key : keys)
            for (var lock : locks)
                if (lock.fits(key)) fits++;

        return fits;
    }


    record Key(int[] heights) {
        Key(CharMatrix schematic) {
            var heights = new int[5];
            for (int i = 0; i < 5; i++) {
                for (char c : schematic.col(i)) {
                    if (c == '.') continue;
                    heights[i]++;
                }
            }
            this(heights);
        }
    }

    record Lock(int[] heights) {
        Lock(CharMatrix schematic) {
            var heights = new int[5];
            for (int i = 0; i < 5; i++) {
                for (char c : schematic.col(i)) {
                    if (c == '.') break;
                    heights[i]++;
                }
            }
            this(heights);
        }

        boolean fits(Key key) {
            for (int i = 0; i < 5; i++) {
                if (heights[i] + key.heights[i] > MAX_LOCK_HEIGHT)
                    return false;
            }
            return true;
        }
    }
}