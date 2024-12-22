package year2024.day21;

import aoc.AdventOfCode;
import aoc.Task;
import util.CharMatrix;
import util.IPoint;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Dennis Woithe
 */
public class Task_A extends Task {

    static {
        new Task_A();
    }

    static final CharMatrix KEYPAD_1 = new CharMatrix(new char[][]{
            {'7', '8', '9'},
            {'4', '5', '6'},
            {'1', '2', '3'},
            {' ', '0', 'A'},
    });

    static final CharMatrix KEYPAD_2 = new CharMatrix(new char[][]{
            {' ', '^', 'A'},
            {'<', 'v', '>'},
    });

    static final CharMatrix.CoordValCharPredicate NOT_EMPTY = (_, _, v) -> v != ' ';

    @Override
    protected Object exec(AdventOfCode aoc) {
        return aoc.inputStr()
                .mapToLong(line -> {
                    var num = Long.parseLong(line.substring(0, line.length() - 1));
                    var shortest = fewestPresses(3, 3, line.chars().mapToObj(c -> (Character) (char) c).collect(Collectors.toList()));
                    return num * shortest;
                })
                .sum();
    }

    long fewestPresses(int maxDepth, int depth, List<Character> sequence) {
        System.out.println(" ".repeat(maxDepth - depth) + depth + ") sequence: " + sequence);
        if (depth == 0)
            return sequence.size();
        var sum = 0L;
        var last = 'A';
        for (var c : sequence) {
            sum += shortestPathsFor(depth == maxDepth ? KEYPAD_1 : KEYPAD_2, last, c)
                    .stream()
                    .map(ArrayList::new)
                    .map(path -> {
                        var buttons = new ArrayList<Character>();
                        for (var i = 0; i < path.size() - 1; i++)
                            buttons.add(path.get(i).neighbourAt(path.get(i + 1)).toChar());
                        if (depth - 1 != maxDepth)
                            buttons.add('A');
                        return buttons;
                    })
                    .mapToLong(buttons -> fewestPresses(maxDepth, depth - 1, buttons))
                    .min()
                    .orElseThrow();
            last = c;
        }
        System.out.println(" ".repeat(maxDepth - depth) + depth + ") sum: " + sum);
        return sum;
    }

    Set<SequencedSet<IPoint>> shortestPathsFor(CharMatrix keypad, char from, char to) {
        return keypad.shortestPaths(keypad.findFirst(from), keypad.findFirst(to), NOT_EMPTY);
    }

}