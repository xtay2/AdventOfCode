package year2022.day09;

import aoc.AdventOfCode;
import aoc.Task;

import java.awt.*;
import java.util.HashSet;
import java.util.Map;

import static java.lang.Math.abs;

public class Task_A extends Task {

	static {
		new Task_A();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		var head = new Point(0, 0);
		var tail = new Point();
		var horiDisp = Map.of("L", -1, "U", 0, "R", 1, "D", 0);
		var vertDisp = Map.of("L", 0, "U", -1, "R", 0, "D", 1);
		var movements = new HashSet<Point>();
		movements.add(tail);
		for (var line : aoc.inputLst()) {
			var d = line.split(" ")[0];
			var amt = Integer.parseInt(line.split(" ")[1]);
			for (int x = 0; x < amt; x++) {
				head = new Point(head.x + vertDisp.get(d), head.y + horiDisp.get(d));
				tail = moveTail(head, tail);
				movements.add(tail);
			}
		}
		return movements.size();
	}

	static Point moveTail(Point head, Point tail) {
		int // Distanz
				distHori = head.x - tail.x,
				distVert = head.y - tail.y;
		int // Bewegung
				moveHori = tail.x < head.x ? head.x - 1 : head.x + 1,
				moveVert = tail.y < head.y ? head.y - 1 : head.y + 1;
		if (abs(distHori) > 1 || abs(distVert) > 1) {
			if (abs(distHori) >= 2 && abs(distVert) >= 2)
				tail = new Point(moveHori, moveVert);
			else if (abs(distHori) >= 2)
				tail = new Point(moveHori, head.y);
			else if (abs(distVert) >= 2)
				tail = new Point(head.x, moveVert);
		}
		return tail;
	}
}