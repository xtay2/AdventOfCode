package year2021.day16;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.ArrayList;
import java.util.Objects;

public class Task_B extends Task {

	static {
		new Task_B();
	}

	static int versionSum = 0;

	@Override
	protected Object exec(AdventOfCode aoc) {
		return extracted(toBinary(aoc.inputLst().get(0)))[1];
	}

	private static String toBinary(String string) {
		var out = new StringBuilder();
		String[] map = {
				"0000", // 0
				"0001", // 1
				"0010", // 2
				"0011", // 3
				"0100", // 4
				"0101", // 5
				"0110", // 6
				"0111", // 7
				"1000", // 8
				"1001", // 9
				"1010", // A
				"1011", // B
				"1100", // C
				"1101", // D
				"1110", // E
				"1111"  // F
		};
		for (char c : string.toCharArray())
			out.append(map[Character.digit(c, 16)]);
		return out.toString();
	}

	static long b2nr(String binary) {
		return Long.valueOf(binary, 2);
	}

	static long[] extracted(String input) {
		if (input.matches("0+") || input.length() <= 7)
			return new long[]{input.length(), -1};
		System.out.println("\n" + input);
		long version = b2nr(input.substring(0, 3));
		versionSum += version;
		long typeId = b2nr(input.substring(3, 6));
		if (typeId == 4) {
			StringBuilder val = new StringBuilder();
			for (int i = 6; i < input.length(); i += 5) {
				String e = input.substring(i, i + 5);
				val.append(e.substring(1));
				if (e.charAt(0) == '0') {
					System.out.println("Literal: " + b2nr(val.toString()));
					System.out.println("Length of Literal : " + (i + 5));
					return new long[]{i + 5, b2nr(val.toString())};
				}
			}
			return new long[]{input.length(), b2nr(val.toString())};
		}
		// Operator
		int lengthTypeId = input.charAt(6) == '1' ? 11 : 15;
		long length = b2nr(input.substring(7, 7 + lengthTypeId));
		System.out.println("Operator. V: " + version + " Length: " + length + " with IDl " + lengthTypeId);
		int nextStart = 7 + lengthTypeId;
		ArrayList<Long> values = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			long[] inside = extracted(input.substring(nextStart));
			nextStart += inside[0];
			if (inside[1] != -1)
				values.add(inside[1]);
		}
		System.out.println(values + " " + execute(values, (int) typeId));
		return new long[]{nextStart, execute(values, (int) typeId)};
	}

	private static long execute(ArrayList<Long> values, int typeId) {
		return switch (typeId) {
			case 0 -> values.stream().reduce(0L, Long::sum);
			case 1 -> values.stream().reduce(1L, (a, b) -> a * b);
			case 2 -> values.stream().reduce(Long.MAX_VALUE, Math::min);
			case 3 -> values.stream().reduce(0L, Math::max);
			case 5 -> values.get(0) > values.get(1) ? 1 : 0;
			case 6 -> values.get(0) < values.get(1) ? 1 : 0;
			case 7 -> Objects.equals(values.get(0), values.get(1)) ? 1 : 0;
			default -> throw new IllegalArgumentException("Unexpected value: " + typeId);
		};
	}
}