package aoc2021.day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Test {
	public static void main(String[] args) throws IOException {
		ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("input")));
		System.out.println(simulate(1, txt2intArr(lines.get(0).split(","))));
	}

	private static long simulate(int days, int[] input) {
		int cycle = 7;
		long fishCnt[] = new long[cycle];
		long newFishCnt[] = new long[cycle];
		for (int i = 0; i < input.length; i++)
			fishCnt[input[i]]++;

		for (int i = 0; i < cycle; i++) {
			if (fishCnt[i] != 0) {
				newFishCnt[i] = fishCnt[wrap(days, cycle - 1, i)];
			}
		}
		System.out.println(Arrays.toString(fishCnt));
		System.out.println(Arrays.toString(newFishCnt));

		long sum = 0;
		for (long f : newFishCnt)
			sum += f;
		return sum;
	}

	private static int wrap(int n, int cycle, int state) {
		int res = 0;
		if (n == 0)
			res = 0;
		else
			res = cycle - (n - 1) % (cycle + 1);
		System.out.println("In " + n + " day" + (n > 1 ? "s" : "") + ", state " + state + " goes to " + (res - state) % cycle);
		return res;
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
