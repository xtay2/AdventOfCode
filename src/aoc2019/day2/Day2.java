package aoc2019.day2;

import java.util.Arrays;

import files.FileManager;

public class Day2 {
	public static void main(String[] args) {

		for (int i = 0; i < 99; i++) {
			for (int j = 0; j < 99; j++) {
				try {
					if (extracted(i, j) == 19690720) {
						System.out.println("I: " + i + "J:" + j);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					
				}
			}
		}
	}

	static int extracted(int a, int b) {
		String[] p = FileManager.readFile("input").split(",");
		int[] p2 = new int[p.length];
		for (int i = 0; i < p.length; i++)
			p2[i] = Integer.valueOf(p[i]);
		p2[0] = 1;
		p2[1] = a;
		p2[2] = b;
		int pc = 0;
		while (pc < p.length) {
			if (p2[pc] == 1)
				p2[p2[pc + 3]] = p2[p2[pc + 1]] + p2[p2[pc + 2]];
			else if (p2[pc] == 2)
				p2[p2[pc + 3]] = p2[p2[pc + 1]] * p2[p2[pc + 2]];
			else if (p2[pc] == 99)
				break;
			pc += 4;
		}
		return p2[0];
	}
}
