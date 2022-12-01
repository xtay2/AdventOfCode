package year2021.day02;

import aoc.AdventOfCode;
import aoc.Task;

public class Task_B extends Task {

	static {
		new Task_B();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		var lines = aoc.inputArr();
		int horizontal = 0, depth = 0, aim = 0;
		for (String line : lines) {
			int val = Integer.parseInt(line.split(" ")[1]);
			switch (line.split(" ")[0]) {
				case "forward" -> {
					horizontal += val;
					depth += aim * val;
				}
				case "down" -> aim += val;
				case "up" -> aim -= val;
			}
		}
		return horizontal * depth;
	}

}
