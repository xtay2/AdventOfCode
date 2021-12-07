package aoc2021.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Day7 {

	static int n;
	static final int m = 0;

	public static void main(String[] args) throws IOException {
		ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("input")));
		System.out.println("Task 1: " + task1(lines.get(0).split(",")));
		System.out.println("Task 2: " + task2(lines.get(0).split(",")));
	}

	static long task1(String[] input) {
		int smallestVal = Integer.MAX_VALUE;
		for (long i = 0; i < 1000; i++) {
			int sum = 0;
			for (String e : input)
				sum += Math.abs(i - txt2nr(e));
			if (sum < smallestVal)
				smallestVal = sum;
		}
		return smallestVal;
	}

	static int task2(String[] input) {
		int smallestVal = Integer.MAX_VALUE;
		for (int i = 0; i < 1000; i++) {
			int sum = 0;
			for (String e : input) {
				int fuel = 0;
				for (int j = 1; j <= Math.abs(i - txt2nr(e)); j++)
					fuel += j;
				sum += fuel;
			}
			if (sum < smallestVal)
				smallestVal = sum;
		}
		return smallestVal;
	}

	static int txt2nr(String s) {
		return Integer.valueOf(s);
	}
}