package year2015.day02;

import aoc.AdventOfCode;
import aoc.Task;
import helper.util.Generator;

import static helper.math.MathHelper.*;

public class Task_A extends Task {

	static {
		new Task_A();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		return new Generator<>(aoc.inputLst())
				.mapWrap(l -> l.split("x"))
				.map(g -> g.map(Integer::parseInt).list())
				.reduce(0, (a, b) -> a + surface(b.get(0), b.get(1), b.get(2)));
	}

	static int surface(int l, int w, int h) {
		int x = l * w, y = w * h, z = h * l;
		return 2 * (x + y + z) + min(x, y, z);
	}
}
