package year2022.day01;

import aoc.AdventOfCode;
import aoc.Task;
import helper.util.ArrayHelper;

import static helper.util.StreamHelper.reverse;
import static java.util.Arrays.stream;

public class Task_B extends Task {

	static {
		new Task_B();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		return reverse(stream(aoc.inputTxt().split("\n\n"))
				.mapToInt(group -> ArrayHelper.parseIntStream(group.split("\n")).sum())
				.sorted()
		).limit(3).sum();
	}
}

