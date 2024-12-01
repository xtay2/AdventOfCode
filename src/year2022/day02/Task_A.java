package year2022.day02;

import aoc.AdventOfCode;
import aoc.Task;


public class Task_A extends Task {

	static {
		new Task_A();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		return aoc.inputStr()
				.map(l -> l.split(" "))
				.map(a -> new Integer[]{a[0].charAt(0) - 'A' + 1, a[1].charAt(0) - 'X' + 1}) // Konvertiere zu zahlen
				.map(a -> (a[0] == a[1] // DRAW
						? 3
						: (a[0] == (a[1] + 1) % 3 + 1) // WIN
						? 6
						: 0)
						+ a[1] // HAND
				)
				.reduce(0, Integer::sum);
	}
}
