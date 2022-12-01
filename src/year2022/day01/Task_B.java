package year2022.day01;

import aoc.AdventOfCode;
import aoc.Task;
import helper.util.Generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Task_B extends Task {

	static {
		new Task_B();
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
				.map(i -> ((Integer) i))
				.sorted(Comparator.reverseOrder())
				.mapToInt(i -> i)
				.limit(3)
				.sum();
	}
}

