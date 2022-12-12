package year2022.day12;

import aoc.AdventOfCode;
import aoc.Task;
import helper.util.Matrix;

public class Task_A extends Task {

	static {
		new Task_A();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		var matrix = new Matrix<Character>(aoc.inputTxt());
		var pos = matrix.filter((m, p) -> m.get(p).equals('C')).findFirst().orElseThrow().y;
		var target = matrix.filter((m, p) -> m.get(p).equals('E')).findFirst().orElseThrow().y;
		return matrix.map(c -> c == 'E' ? 'z' : (c == 'S' ? 'a' : c)).toGraph().shortestPath(pos, target).length();
	}
}