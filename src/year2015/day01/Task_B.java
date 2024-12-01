package year2015.day01;

import aoc.AdventOfCode;
import aoc.Task;

public class Task_B extends Task {

	static {
		new Task_B();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		int cnt = 0, idx = 1;
		for (char c : aoc.inputChars()) {
			cnt += c == '(' ? 1 : -1;
			idx++;
			if (cnt == -1)
				return idx;
		}
		return "Found nothing. Ended with: " + cnt;
	}
}
