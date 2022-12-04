package year2022.day03;

import aoc.AdventOfCode;
import aoc.Task;

public class Task_B extends Task {

	static {
		new Task_B();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		int prio = 0;
		var lines = aoc.inputLst();
		while (!lines.isEmpty()) {
			char c = inThree(lines.remove(0), lines.remove(0), lines.remove(0));
			prio += Character.isUpperCase(c) ? c - 'A' + 27 : c - 'a' + 1;
		}
		return prio;
	}

	static Character inThree(String a, String b, String c) {
		for (char c1 : a.toCharArray()) {
			if (b.indexOf(c1) != -1 && c.indexOf(c1) != -1)
				return c1;
		}
		return null;
	}
}
