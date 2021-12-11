package aoc2021.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import static java.lang.Math.*;

public class Day12 {

	static int n;
	static final int m = 0;

	public static void main(String[] args) throws IOException {
		ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("input")));
		System.out.println("Task 1: " + task1(lines));
		System.out.println("Task 2: " + task2(lines));
	}

	static long task1(ArrayList<String> input) {
		n = input.size();
		for (int i = 0; i < n; i++) {
			String e = input.get(i);

		}
		long cnt = 0;
		return cnt;
	}

	static long task2(ArrayList<String> input) {
		n = input.size();
		for (int i = 0; i < n; i++) {
			String e = input.get(i);

		}
		long cnt = 0;
		return cnt;
	}

	static int txt2nr(String s) {
		return Integer.valueOf(s);
	}

	static int txt2nr(char s) {
		return Integer.valueOf(s);
	}

	static int nthChar2nr(String s, int index) {
		return txt2nr(s.charAt(index));
	}

	static int[] txt2intArr(String[] line) {
		int[] res = new int[line.length];
		for (int i = 0; i < line.length; i++)
			res[i] = txt2nr(line[i]);
		return res;
	}

	static double rnd(double val, int comma) {
		return Math.round(val * Math.pow(10, comma)) / Math.pow(10, comma);
	}
}