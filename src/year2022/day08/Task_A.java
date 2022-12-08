package year2022.day08;

import aoc.AdventOfCode;
import aoc.Task;
import helper.util.Matrix;

public class Task_A extends Task {

	static {
		new Task_A();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		return new Matrix<Character>(aoc.inputTxt())
				.map(Character::getNumericValue)
				.filter((m, p) -> {
					var val = m.get(p);
					return m.row(p.y, 0, p.x - 1).mapToInt(x -> x).max().orElse(-1) < val
							|| m.row(p.y, p.x + 1).mapToInt(x -> x).max().orElse(-1) < val
							|| m.col(p.x, 0, p.y - 1).mapToInt(x -> x).max().orElse(-1) < val
							|| m.col(p.x, p.y + 1).mapToInt(x -> x).max().orElse(-1) < val;
				})
				.count();
	}
}
