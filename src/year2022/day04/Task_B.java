package year2022.day04;

import aoc.AdventOfCode;
import aoc.Task;

import static java.util.Arrays.stream;

public class Task_B extends Task {

	static {
		new Task_B();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		return aoc.inputLst().stream()
				.map(line -> stream(line.split(","))
						.flatMapToInt(group -> stream(group.split("-"))
								.mapToInt(Integer::parseInt)
						).toArray()
				)
				.filter(v -> contains(v[0], v[1], v[2], v[3]))
				.count();
	}


	boolean contains(int lower1, int upper1, int lower2, int upper2) {
		return (lower1 <= lower2 && lower2 <= upper1)
				|| (lower2 <= lower1 && lower1 <= upper2);
	}
}
