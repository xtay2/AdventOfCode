package year2022.day08;

import aoc.AdventOfCode;
import aoc.Task;
import helper.util.Matrix;

import static helper.StreamHelper.reverse;
import static helper.StreamHelper.takeWhileIncl;

public class Task_B extends Task {

	static {
		new Task_B();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		return new Matrix<Character>(aoc.inputTxt())
				.map(Character::getNumericValue)
				.map((m, p) -> {
					var val = m.get(p);
					return takeWhileIncl(reverse(m.row(p.y, 0, p.x - 1)), v -> v < val).count()
							* takeWhileIncl(m.row(p.y, p.x + 1), v -> v < val).count()
							* takeWhileIncl(reverse(m.col(p.x, 0, p.y - 1)), v -> v < val).count()
							* takeWhileIncl(m.col(p.x, p.y + 1), v -> v < val).count();
				})
				.peek(System.out::println)
				.toStream()
				.mapToLong(i -> i)
				.max().orElse(-1);
	}
}
