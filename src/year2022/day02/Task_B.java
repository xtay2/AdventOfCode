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
					a[1] = (a[0] + a[1]) % 3;
					int hand = a[1] + 1; // +1 wegen Modulo
					return (a[0] - 1 == a[1] // DRAW
							? 3
							: a[0] % 3 == a[1]  // WIN
							? 6
							: 0)
							+ hand;  // HAND (1, 2, 3)
				})
				.reduce(0, Integer::sum);
	}
}
