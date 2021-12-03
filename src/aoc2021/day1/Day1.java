package aoc2021.day1;

import files.FileManager;

public class Day1 {

	public static void main(String[] args) {
		String[] lines = FileManager.fileToLineArray("input");
		System.out.println("Singlestep Counter: " + task1(lines));
		System.out.println("Threesteps Counter: " + task2(lines));
	}

	static int task1(String[] lines) {
		int counter = 0;
		int lastSum = txt2nr(lines[0]);
		for (int i = 1; i + 1 < lines.length; i++) {
			int currentSum = Integer.valueOf(lines[i]);
			if (currentSum > lastSum)
				counter++;
			lastSum = currentSum;
		}
		return counter;
	}

	static int task2(String[] lines) {
		int counter = 0;
		int lastSum = txt2nr(lines[0]) + txt2nr(lines[1]) + txt2nr(lines[2]);
		for (int i = 1; i + 2 < lines.length; i++) {
			int currentSum = txt2nr(lines[i]) + txt2nr(lines[i + 1]) + txt2nr(lines[i + 2]);
			if (currentSum > lastSum)
				counter++;
			lastSum = currentSum;
		}
		return counter;
	}

	static int txt2nr(String s) {
		return Integer.valueOf(s);
	}

}
