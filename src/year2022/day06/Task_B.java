package year2022.day06;

import aoc.AdventOfCode;
import aoc.Task;

public class Task_B extends Task {

	static {
		new Task_B();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		var n = 17;
		var input = aoc.inputTxt();
		for (int i = 0; i < input.length() - n; i++) {
			if (input.substring(i, i + n).chars().distinct().count() == n)
				return i + n;
		}
		return "Not found";
	}
	
}
