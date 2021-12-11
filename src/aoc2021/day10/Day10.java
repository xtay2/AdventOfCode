package aoc2021.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day10 {

	static final HashMap<Character, Integer> BR_NR = new HashMap<>(
			Map.of('(', 0, '[', 1, '{', 2, '<', 3, ')', 4, ']', 5, '}', 6, '>', 7));
	
	static final int[] VALUES = { 3, 57, 1197, 25137 };

	static void main(String[] args) throws IOException {
		ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("input")));
		System.out.println("Error Score: " + task1(lines));
		System.out.println("Middle Score: " + task2(lines));
	}

	private static Stack<Integer> stack;

	private static long findCorrupted(String line) {
		stack = new Stack<>();
		for (char letter : line.toCharArray()) {
			int l = BR_NR.get(letter);
			if (l < 4)
				stack.push(l);
			else if (stack.pop() != (l % 4))
				return VALUES[l % 4];
		}
		return 0;
	}

	static long task1(ArrayList<String> lines) {
		long cnt = 0;
		for (String line : lines)
			cnt += findCorrupted(line);
		return cnt;
	}

	public static long task2(ArrayList<String> lines) {
		ArrayList<Long> scores = new ArrayList<>();
		for (String line : lines) {
			if (findCorrupted(line) == 0) {
				long cnt = 0;
				while (!stack.isEmpty())
					cnt = (cnt * 5) + stack.pop() + 1;
				scores.add(cnt);
			}
		}
		Collections.sort(scores);
		return scores.get(scores.size() / 2);
	}

}