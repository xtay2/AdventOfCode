package aoc2021.day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Day3 {

	static final int N = 12;

	public static void main(String[] args) throws IOException {
		ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("input")));
		System.err.println("Most/Least common bits mult: " + task1(lines) + "\n");
		System.err.println("CO2 scrubber rating: " + task2(lines));
	}

	static int task1(ArrayList<String> lines) {
		int[] ones = new int[N];
		int[] zeroes = new int[N];
		for (int i = 0; i < N; i++) {
			for (String l : lines) {
				if (l.charAt(i) == '1')
					ones[i]++;
				if (l.charAt(i) == '0')
					zeroes[i]++;
			}
		}
		System.out.println("Zeroes: " + Arrays.toString(zeroes));
		System.out.println("Ones: " + Arrays.toString(ones));
		String gamma = "";
		String epsilon = "";
		for (int i = 0; i < N; i++) {
			if (ones[i] > zeroes[i]) {
				gamma += "1";
				epsilon += "0";
			} else {
				gamma += "0";
				epsilon += "1";
			}
		}
		System.out.println("Gamma: " + gamma + ", Epsilon: " + epsilon);
		return Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2);
	}

	static int task2(ArrayList<String> lines) {
		return Integer.parseInt(most(new ArrayList<>(lines)), 2) * Integer.parseInt(least(new ArrayList<>(lines)), 2);
	}

	static String most(ArrayList<String> most) {
		int[] ones = new int[N];
		int[] zeroes = new int[N];
		for (int i = 0; i < N; i++) {
			for (String l : most) {
				if (l.charAt(i) == '1')
					ones[i]++;
				if (l.charAt(i) == '0')
					zeroes[i]++;
			}
			System.out.println(Arrays.toString(zeroes));
			System.out.println(Arrays.toString(ones));
			for (int j = most.size() - 1; j >= 0; j--) {
				if (most.size() == 1)
					break;
				if (most.get(j).charAt(i) == '0' && ones[i] == zeroes[i])
					most.remove(j);
				else if (most.get(j).charAt(i) == '0' && ones[i] > zeroes[i])
					most.remove(j);
				else if (most.get(j).charAt(i) == '1' && ones[i] < zeroes[i])
					most.remove(j);
			}
		}
		return most.get(0);
	}

	static String least(ArrayList<String> least) {
		int[] ones = new int[N];
		int[] zeroes = new int[N];
		for (int i = 0; i < N; i++) {
			for (String l : least) {
				if (l.charAt(i) == '1')
					ones[i]++;
				if (l.charAt(i) == '0')
					zeroes[i]++;
			}
			System.out.println(Arrays.toString(zeroes));
			System.out.println(Arrays.toString(ones));
			for (int j = least.size() - 1; j >= 0; j--) {
				if (least.size() == 1)
					break;
				if (least.get(j).charAt(i) == '1' && ones[i] == zeroes[i])
					least.remove(j);
				else if (least.get(j).charAt(i) == '0' && ones[i] < zeroes[i])
					least.remove(j);
				else if (least.get(j).charAt(i) == '1' && ones[i] > zeroes[i])
					least.remove(j);
			}
		}
		return least.get(0);
	}
}