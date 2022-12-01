package year2021.day17;

import aoc.AdventOfCode;
import aoc.Task;

public class Task_B extends Task {

	static {
		new Task_B();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		String[] line = aoc.inputTxt().strip().split(":")[1].split(",");
		return compute(
				txt2nr(line[0].split("=")[1].split("\\.\\.")[0]),
				txt2nr(line[0].split("=")[1].split("\\.\\.")[1]),
				txt2nr(line[1].split("=")[1].split("\\.\\.")[0]),
				txt2nr(line[1].split("=")[1].split("\\.\\.")[1])
		);
	}

	private static int compute(int targetminX, int targetmaxX, int targetminY, int targetmaxY) {
		final int maxVelY = -targetminY - 1;
		int highestY = 0;
		int hits = 0;
		for (int velY = targetminY; velY <= maxVelY; velY++) {
			for (int velX = 1; velX <= targetmaxX; velX++) {
				int x = 0, y = 0;
				int ivx = velX, ivy = velY;
				while (x < targetmaxX && y > targetminY) {
					x += ivx;
					y += ivy;
					ivx--;
					ivy--;
					if (y > highestY)
						highestY = y;
					if (x >= targetminX && x <= targetmaxX && y >= targetminY && y <= targetmaxY) {
						hits++;
						break;
					}
					if (ivx < 0)
						ivx = 0;
				}
			}
		}
		return hits;
	}

	static int txt2nr(String s) {
		return Integer.parseInt(s);
	}
}