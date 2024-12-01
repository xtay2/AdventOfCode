package year2022.day05;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.*;

public class Task_A extends Task {

	static {
		new Task_A();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		var lines = aoc.inputLst();
		List<Character>[] stacks = buildStacks(lines); // Parse Crates

		for (var line : lines) {
			if (!line.isBlank()) {
				move(
						stacks,
						parseInt(line.substring(line.indexOf("move ") + 5, line.indexOf(" from"))),
						parseInt(line.substring(line.indexOf("from ") + 5, line.indexOf(" to"))),
						parseInt(line.substring(line.indexOf("to ") + 3))
				);
			}
		}

		return Arrays.stream(stacks).map(e -> e.get(0).toString()).collect(Collectors.joining(""));
	}

	@SuppressWarnings("unchecked")
	List<Character>[] buildStacks(List<String> lines) {
		List<Character>[] stacks = new ArrayList[9];
		Arrays.setAll(stacks, i -> new ArrayList<>());
		while (lines.get(0).startsWith("[")) {
			var line = lines.remove(0);
			for (int i = 0; i < stacks.length; i++) {
				var c = line.charAt((i * 4) + 1);
				if (c != ' ')
					stacks[i].add(c);
			}
		}
		lines.remove(0); // Delete numbered line
		return stacks;
	}

	void move(List<Character>[] stacks, int amount, int from, int to) {
		for (int moves = 0; moves < amount; moves++) {
			var crate = stacks[from - 1].remove(0);
			stacks[to - 1].add(0, crate);
		}
	}
}
