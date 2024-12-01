package year2022.day08;

import aoc.AdventOfCode;
import aoc.Task;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
//        return Matrix.fromString(aoc.inputTxt())
//                .map(Character::getNumericValue)
//                .map((m, p) -> {
//                    var val = m.get(p);
//                    return takeWhileIncl(stream(reverse(m.row(p.y, 0, p.x - 1))), v -> v < val).count()
//                            * takeWhileIncl(stream(m.row(p.y, p.x + 1)), v -> v < val).count()
//                            * takeWhileIncl(stream(reverse(m.col(p.x, 0, p.y - 1))), v -> v < val).count()
//                            * takeWhileIncl(stream(m.col(p.x, p.y + 1)), v -> v < val).count();
//                })
//                .toStream()
//                .mapToLong(i -> i)
//                .max().orElse(-1);
        return null; // TODO: Reimplement me!
    }
}