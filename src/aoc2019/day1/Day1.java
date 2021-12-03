package aoc2019.day1;

import files.FileManager;

public class Day1 {
	public static void main(String[] args) {
		long fuel = 0;
		for (String line : FileManager.fileToLineArray("input")) {
			int nr = Integer.valueOf(line);
			fuel += extracted(nr);
		}
		System.out.println(fuel);
	}

	private static long extracted(long fuel) {
		fuel = (fuel / 3) - 2;
		if (fuel <= 0)
			return 0;
		return fuel + extracted(fuel);
	}
}
