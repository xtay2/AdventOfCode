package year2015.day02;

import aoc.AdventOfCode;
import aoc.Task;

import static helper.base.MathHelper.*;
import static java.util.Arrays.stream;

public class Task_B extends Task {

	static {
		new Task_B();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		return aoc.inputStr()
				.map(l -> stream(l.split("x"))
						.mapToInt(Integer::parseInt)
						.toArray()
				).reduce(0, (a, b) -> a + ribbon(b[0], b[1], b[2]), Integer::sum);
	}

	static int ribbon(int l, int w, int h) {
		return ((l + w + h) - max(l, w, h)) * 2 + l * w * h;
	}
}
