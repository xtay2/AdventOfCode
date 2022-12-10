package year2022.day11;

import aoc.AdventOfCode;
import aoc.Task;

public class Task_A extends Task {

	static {
		new Task_A();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		var lines = aoc.inputLst();
		int exec = 0, wait = 0;
		int cnt = 0;
		for (int i = 0; i < lines.size(); i++) {
			if (((i + 1) - 20) % 40 == 0)
				cnt += exec * (i + 1);
			exec = wait;
			wait = lines.get(i).startsWith("noop") ? 0 : Integer.parseInt(lines.get(i).split(" ")[1]);
		}
		return cnt;
	}
}
