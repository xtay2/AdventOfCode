package year2015.day02;

import aoc.AdventOfCode;
import aoc.Task;
import helper.util.Generator;

import static helper.math.MathHelper.max;

public class Task_B extends Task {

	static {
		new Task_B();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		return new Generator<>(aoc.inputLst())
				.mapWrap(l -> l.split("x"))
				.map(g -> g.map(Integer::parseInt).list())
				.reduce(0, (a, b) -> a + ribbon(b.get(0), b.get(1), b.get(2)));
	}

	static int ribbon(int l, int w, int h) {
		return ((l + w + h) - max(l, w, h)) * 2 + l * w * h;
	}
}
