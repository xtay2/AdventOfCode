package year2022.day01;

import aoc.AdventOfCode;
import aoc.Task;
import helper.util.Generator;

public class Task_A extends Task {

	static {
		new Task_A();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		return new Generator<>(aoc.inputTxt())
				.map(f -> new Generator<>(f.split("\n\n"))
						.map(v -> new Generator<>(v.split("\n"))
								.map(Integer::parseInt)
								.reduce(0, Integer::sum))
				)
				.flatten()
				.stream()
				.mapToInt(i -> ((Integer) i))
				.max()
				.getAsInt();
	}
}

