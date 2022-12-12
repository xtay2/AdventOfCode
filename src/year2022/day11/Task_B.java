package year2022.day11;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;
import static year2022.day11.Task_B.Monkey.monkeys;


public class Task_B extends Task {

	static {
		new Task_B();
	}

	static int lcd = 1;

	@Override
	protected Object exec(AdventOfCode aoc) {
		var input = aoc.inputLst();
		while (!input.isEmpty()) {
			if (input.get(0).isBlank() || input.get(0).startsWith("Monkey")) {
				input.remove(0);
				continue;
			}
			monkeys.add(new Monkey(
					stream(input.remove(0).split(": ")[1].split(", ")).mapToLong(Long::parseLong).toArray(),
					buildExp(input.remove(0).split("new = ")[1]),
					parseInt(input.remove(0).split("divisible by ")[1]),
					parseInt(input.remove(0).split("monkey ")[1]),
					parseInt(input.remove(0).split("monkey ")[1])
			));
		}
		lcd = monkeys.stream().map(m -> m.divBy).reduce((a, b) -> { // LCD-Algorithm
			int higher = Math.max(a, b), lower = Math.min(a, b), d = higher;
			while (d % lower != 0)
				d += higher;
			return d;
		}).orElseThrow();

		for (int round = 0; round < 10000; round++)
			monkeys.forEach(Monkey::throwItems);

		return monkeys.stream().map(m -> m.business)
				.sorted(Comparator.reverseOrder())
				.limit(2).reduce((a, b) -> a * b).orElseThrow();
	}

	static Function<Long, Long> buildExp(String exp) {
		if (exp.equals("old * old"))
			return x -> x * x;
		int y = parseInt(exp.split(" . ")[1]);
		if (exp.matches("old \\+ \\d+"))
			return x -> x + y;
		if (exp.matches("old \\* \\d+"))
			return x -> x * y;
		throw new AssertionError("Unknown Expression: " + exp);
	}

	static class Monkey {

		static List<Monkey> monkeys = new ArrayList<>();

		long business = 0;

		final List<Long> items = new ArrayList<>();
		final Function<Long, Long> operation;
		final int divBy, targetTrue, targetFalse;

		public Monkey(long[] items, Function<Long, Long> operation, int divBy, int targetTrue, int targetFalse) {
			this.items.addAll(stream(items).boxed().toList());
			this.operation = operation;
			this.divBy = divBy;
			this.targetTrue = targetTrue;
			this.targetFalse = targetFalse;
		}

		void throwItems() {
			while (!items.isEmpty()) {
				business++;
				long worry = operation.apply(items.remove(0)) % lcd;
				monkeys.get(worry % divBy == 0 ? targetTrue : targetFalse).items.add(worry);
			}
		}
	}
}