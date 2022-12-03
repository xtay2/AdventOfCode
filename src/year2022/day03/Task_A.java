package year2022.day03;

import aoc.AdventOfCode;
import aoc.Task;

public class Task_A extends Task {

	static {
		new Task_A();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		int prio = 0;
		for (var line : aoc.inputLst()) {
			var c = inBoth(line.substring(0, line.length() / 2), line.substring(line.length() / 2));
			prio += Character.isUpperCase(c) ? c - 'A' + 27 : c - 'a' + 1;

		}
		return prio;
	}

	static Character inBoth(String a, String b) {
		for (char c1 : a.toCharArray()) {
			for (char c2 : b.toCharArray()) {
				if (c1 == c2)
					return c1;
			}
		}
		return null;
	}
}
