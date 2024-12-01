package year2022.day08;

import aoc.AdventOfCode;
import aoc.Task;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
//        return Matrix.fromString(aoc.inputTxt())
//                .map(Character::getNumericValue)
//                .filter((m, p) -> {
//                    var val = m.get(p);
//                    return stream(m.row(p.y, 0, p.x - 1)).mapToInt(x -> x).max().orElse(-1) < val
//                            || stream(m.row(p.y, p.x + 1)).mapToInt(x -> x).max().orElse(-1) < val
//                            || stream(m.col(p.x, 0, p.y - 1)).mapToInt(x -> x).max().orElse(-1) < val
//                            || stream(m.col(p.x, p.y + 1)).mapToInt(x -> x).max().orElse(-1) < val;
//                })
//                .count();
        return null; // TODO: Reimplement me!
    }
}