package year2022.day04;

import aoc.AdventOfCode;
import aoc.Task;
import helper.util.Generator;

public class Task_B extends Task {

	static {
		new Task_B();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		return new Generator<>(aoc.inputLst())
				.map(line -> new Generator<>(line.split(","))
						.map(group -> new Generator<>(group.split("-"))
								.map(Integer::parseInt)
						).flatten()
						.map(v -> (Integer) v)
						.list()
				)
				.filter(v -> contains(v.get(0), v.get(1), v.get(2), v.get(3)))
				.length();
	}


	boolean contains(int lower1, int upper1, int lower2, int upper2) {
		return (lower1 <= lower2 && lower2 <= upper1)
				|| (lower2 <= lower1 && lower1 <= upper2);
	}
}
