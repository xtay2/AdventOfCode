package year2022.day11;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static year2022.day11.Task_A.Monkey.monkeys;


public class Task_A extends Task {

	static {
		new Task_A();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		var input = aoc.inputLst();
		while (!input.isEmpty()) {
			if (input.get(0).isBlank() || input.get(0).startsWith("Monkey")) {
				input.remove(0);
				continue;
			}
			monkeys.add(new Monkey(
					Arrays.stream(input.remove(0).split(": ")[1].split(", ")).mapToInt(Integer::parseInt).toArray(),
					buildExp(input.remove(0).split("new = ")[1]),
					Integer.parseInt(input.remove(0).split("divisible by ")[1]),
					Integer.parseInt(input.remove(0).split("monkey ")[1]),
					Integer.parseInt(input.remove(0).split("monkey ")[1])
			));
		}
		for (int round = 0; round < 20; round++)
			monkeys.forEach(Monkey::throwItems);
		return monkeys.stream().map(m -> m.business)
				.sorted(Comparator.reverseOrder())
				.limit(2).reduce((a, b) -> a * b).orElseThrow();
	}

	static Function<Integer, Integer> buildExp(String exp) {
		if (exp.equals("old * old"))
			return x -> x * x;
		int y = Integer.parseInt(exp.split(" . ")[1]);
		if (exp.matches("old \\+ \\d+"))
			return x -> x + y;
		if (exp.matches("old \\* \\d+"))
			return x -> x * y;
		throw new AssertionError("Unknown Expression: " + exp);
	}

	static class Monkey {

		static List<Monkey> monkeys = new ArrayList<>();

		int business = 0;

		final List<Integer> items = new ArrayList<>();
		final Function<Integer, Integer> operation;
		final int divBy, targetTrue, targetFalse;

		public Monkey(int[] items, Function<Integer, Integer> operation, int divBy, int targetTrue, int targetFalse) {
			this.items.addAll(Arrays.stream(items).boxed().toList());
			this.operation = operation;
			this.divBy = divBy;
			this.targetTrue = targetTrue;
			this.targetFalse = targetFalse;
		}

		void throwItems() {
			while (!items.isEmpty()) {
				business++;
				int worry = operation.apply(items.remove(0)) / 3;
				monkeys.get(worry % divBy == 0 ? targetTrue : targetFalse).items.add(worry);
			}
		}
	}
}