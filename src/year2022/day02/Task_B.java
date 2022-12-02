package year2022.day02;

import aoc.AdventOfCode;
import aoc.Task;
import helper.util.Generator;

import java.util.Arrays;

public class Task_B extends Task {

	static {
		new Task_B();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		return new Generator<>(aoc.inputLst())
				.map(l -> l.split(" "))
				.map(a -> new Integer[]{a[0].charAt(0) - 'A' + 1, a[1].charAt(0) - 'X' + 1}) // Konvertiere zu zahlen
				.map(a -> {
					int x = a[0], y = (x + a[1]) % 3, res = y + 1;
					return (x - 1 == y ? 3 : x % 3 == y ? 6 : 0) + res;
				})
				.reduce(0, Integer::sum);
	}
}
