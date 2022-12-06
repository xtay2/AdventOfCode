package year2022.day06;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.*;
import java.util.stream.Collectors;

import static helper.math.Conversion.chars;

public class Task_A extends Task {

	static {
		new Task_A();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		var n = 4;
		var input = aoc.inputTxt();
		for (int i = 0; i < input.length() - n; i++) {
			if (input.substring(i, i + n).chars().distinct().count() == n)
				return i + n;
		}
		return "Not found";
	}

}
