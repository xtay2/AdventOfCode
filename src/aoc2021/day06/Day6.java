package aoc2021.day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Day6 {

	public static void main(String[] args) throws IOException {
		ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("input")));
		System.out.println("Task 1: " + task1(txt2intArr(lines.get(0).split(","))));
		System.out.println("Task 2: " + task2(txt2intArr(lines.get(0).split(","))));
	}

	static long task1(int[] input) {
		return simulate(input, 80);
	}

	static long task2(int[] input) {
		return simulate(input, 256);
	}

	static long simulate(int[] input, int days) {
		long[] fishCnt = new long[9];
		for (int i = 0; i < input.length; i++)
			fishCnt[input[i]]++;

		for (int day = 0; day < days; day++) {
			long temp = fishCnt[0];
			for (int i = 0; i < 8; i++)
				fishCnt[i] = fishCnt[i + 1];
			fishCnt[6] += temp;
			fishCnt[8] = temp;
		}
		long sum = 0;
		for (long f : fishCnt)
			sum += f;
		return sum;
	}

	static int txt2nr(String s) {
		return Integer.valueOf(s);
	}

	static int[] txt2intArr(String[] line) {
		int[] res = new int[line.length];
		for (int i = 0; i < line.length; i++)
			res[i] = txt2nr(line[i]);
		return res;
	}
}
