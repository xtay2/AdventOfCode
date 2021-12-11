package aoc2021.day07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.lang.Math.*;

public class Day7 {

	static int n;
	static final int m = 1000;

	public static void main(String[] args) throws IOException {
		ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("input")));
		System.out.println("Linear fuel consumption: \t" + task1(lines.get(0).split(",")));
		System.out.println("Exponential fuel consumption: \t" + task2(lines.get(0).split(",")));
	}

	static long task1(String[] input) {
		int smallestVal = Integer.MAX_VALUE;
		for (long i = 0; i < m; i++) {
			int sum = 0;
			for (String e : input)
				sum += abs(i - txt2nr(e));
			smallestVal = min(smallestVal, sum);
		}
		return smallestVal;
	}

	static int task2(String[] input) {
		int smallestVal = Integer.MAX_VALUE;
		for (int i = 0; i < m; i++) {
			int sum = 0;
			for (String e : input)
				sum += (abs(i - txt2nr(e)) + 1) * abs(i - txt2nr(e)) / 2;
			smallestVal = min(smallestVal, sum);
		}
		return smallestVal;
	}

	static int txt2nr(String s) {
		return Integer.valueOf(s);
	}
}