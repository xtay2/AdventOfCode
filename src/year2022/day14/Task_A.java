package year2022.day14;

import aoc.AdventOfCode;
import aoc.Task;
import helper.util.datastructures.tuples.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static helper.util.ArrayMatrixHelper.*;
import static helper.util.ArrayMatrixHelper.fill;
import static helper.util.ArrayMatrixHelper.matches;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Task_A extends Task {

	static {
		new Task_A();
	}

	static final int DIM = 600;

	private enum Material { AIR, ROCK, SAND, SOLID_SAND }

	static Material[][] cave = fill(new Material[DIM][DIM], Material.AIR);

	@Override
	protected Object exec(AdventOfCode aoc) {
		// Create Cave
		aoc.inputLst().stream()
				.map(line -> Arrays.stream(line.split(" -> "))
						.map(tuple -> new Pair<>(tuple.split(","))
								.map(Integer::parseInt)
						).toList()
				).forEach(this::addRocks);

		// Simulate Sand
		while (allSandCanLand(cave) && cave[0][500] == Material.AIR) {
			cave[0][500] = Material.SAND;
			simulate();
		}
		return matches(cave, Material.SOLID_SAND);
	}

	private void addRocks(List<Pair<Integer>> line) {
		var last = line.get(0);
		for (var current : line) {
			if (Objects.equals(last.x, current.x)) {
				for (int i = min(last.y, current.y); i <= max(last.y, current.y); i++)
					cave[i][last.x] = Material.ROCK;
			} else if (Objects.equals(last.y, current.y)) {
				for (int i = min(last.x, current.x); i <= max(last.x, current.x); i++)
					cave[last.y][i] = Material.ROCK;
			} else
				throw new AssertionError(last + " " + current);
			last = current;
		}
	}

	private boolean allSandCanLand(Material[][] cave) {
		for (int offset = 0; offset < DIM; offset++) {
			for (int height = DIM - 1; height >= 0; height--) {
				if (cave[height][offset] == Material.ROCK)
					break;
				else if (cave[height][offset] == Material.SAND)
					return false;
			}
		}
		return true;
	}

	private void simulate() {
		var next = copy(cave, fill(new Material[DIM][DIM], Material.AIR), e -> e == Material.ROCK || e == Material.SOLID_SAND);
		for (int height = DIM - 1; height >= 0; height--) {
			for (int offset = 0; offset < DIM; offset++) {
				if (cave[height][offset] == Material.SAND) {
					if (cave[height + 1][offset] == Material.AIR) { // Down
						next[height + 1][offset] = Material.SAND;
						cave[height][offset] = Material.AIR;
					} else if (cave[height + 1][offset - 1] == Material.AIR) { // Down-Left
						next[height + 1][offset - 1] = Material.SAND;
						cave[height][offset] = Material.AIR;
					} else if (cave[height + 1][offset + 1] == Material.AIR) { // Down-Right
						next[height + 1][offset + 1] = Material.SAND;
						cave[height][offset] = Material.AIR;
					} else // Blocked
						next[height][offset] = cave[height + 1][offset] == Material.SAND
								? Material.SAND
								: Material.SOLID_SAND;
				}
			}
		}
		cave = next;
	}

}
