package year2021.day03;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.Arrays;

public class Task_A extends Task {

	static {
		new Task_A();
	}

	static final int N = 12;

	@Override
	protected Object exec(AdventOfCode aoc) {
		int[] ones = new int[N], zeroes = new int[N];
		for (int i = 0; i < N; i++) {
			for (String l : aoc.inputLst()) {
				if (l.charAt(i) == '1')
					ones[i]++;
				if (l.charAt(i) == '0')
					zeroes[i]++;
			}
		}
		System.out.println("Zeroes: " + Arrays.toString(zeroes));
		System.out.println("Ones: " + Arrays.toString(ones));
		StringBuilder gamma = new StringBuilder();
		StringBuilder epsilon = new StringBuilder();
		for (int i = 0; i < N; i++) {
			if (ones[i] > zeroes[i]) {
				gamma.append("1");
				epsilon.append("0");
			} else {
				gamma.append("0");
				epsilon.append("1");
			}
		}
		System.out.println("Gamma: " + gamma + ", Epsilon: " + epsilon);
		return Integer.parseInt(gamma.toString(), 2) * Integer.parseInt(epsilon.toString(), 2);
	}
}