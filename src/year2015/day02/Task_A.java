package year2015.day02;

import aoc.AdventOfCode;
import aoc.Task;

import static helper.base.MathHelper.min;
import static java.util.Arrays.stream;

public class Task_A extends Task {

	static {
		new Task_A();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		return aoc.inputStr()
				.map(l -> stream(l.split("x"))
						.mapToInt(Integer::parseInt)
						.toArray()
				).reduce(0, (a, b) -> a + surface(b[0], b[1], b[2]), Integer::sum);
	}

	static int surface(int l, int w, int h) {
		int x = l * w, y = w * h, z = h * l;
		return 2 * (x + y + z) + min(x, y, z);
	}
}
