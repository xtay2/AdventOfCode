package aoc2021.day02;

import files.FileManager;

public class Day2 {
	public static void main(String[] args) {
		String[] lines = FileManager.fileToLineArray("input");
		System.out.println("Up and Down: " + task1(lines));
		System.out.println("Diagonal Adjust: " + task2(lines));
	}

	static int task1(String[] lines) {
		int horizontal = 0;
		int depth = 0;
		for (String line : lines) {
			int val = Integer.valueOf(line.split(" ")[1]);
			switch (line.split(" ")[0]) {
			case "forward":
				horizontal += val;
				break;
			case "down":
				depth += val;
				break;
			case "up":
				depth -= val;
				break;
			}
		}
		return horizontal * depth;
	}

	static int task2(String[] lines) {
		int horizontal = 0;
		int depth = 0;
		int aim = 0;
		for (String line : lines) {
			int val = Integer.valueOf(line.split(" ")[1]);
			switch (line.split(" ")[0]) {
			case "forward":
				horizontal += val;
				depth += aim * val;
				break;
			case "down":
				aim += val;
				break;
			case "up":
				aim -= val;
				break;
			}
		}
		return horizontal * depth;
	}

}
