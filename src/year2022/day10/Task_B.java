package year2022.day10;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.ArrayList;

public class Task_B extends Task {

	static {
		new Task_B();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		var changes = new ArrayList<Integer>();
		for (var line : aoc.inputLst()) {
			changes.add(0);
			if (line.startsWith("addx"))
				changes.add(Integer.parseInt(line.split(" ")[1]));
		}
		var x = 1;
		StringBuilder s = new StringBuilder("\n");
		for (int i = 0; i < changes.size(); i++) {
			s.append(((i % 40) == x - 1 || (i % 40) == x || (i % 40) == x + 1) ? "X" : " ");
			if (i % 40 == 39)
				s.append("\n");
			x += changes.get(i);

		}
		return s;
	}
}
