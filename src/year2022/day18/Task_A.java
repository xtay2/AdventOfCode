package year2022.day18;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.Arrays;

public class Task_A extends Task {

	static {
		new Task_A();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		aoc.inputStr().map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).forEach(e -> System.out.println(Arrays.toString(e)));
		return null;
	}
}
