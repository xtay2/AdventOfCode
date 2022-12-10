package year2022.day10;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.ArrayList;

public class Task_A extends Task {

	static {
		new Task_A();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		var changes = new ArrayList<Integer>();
		for (var line : aoc.inputLst()) {
			changes.add(0);
			if (line.startsWith("addx"))
				changes.add(Integer.parseInt(line.split(" ")[1]));
		}
		var res = 0;
		var x = 1;
		for (int i = 0; i < changes.size(); i++) {
			if (i % 40 == 19)
				res += (i + 1) * x;
			x += changes.get(i);
		}
		return res;
	}
}
