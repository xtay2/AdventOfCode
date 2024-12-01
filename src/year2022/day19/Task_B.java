package year2022.day19;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.ArrayList;
import java.util.List;

import static helper.util.ArrayHelper.parseIntStream;

public class Task_B extends Task {

	static {
		new Task_B();
	}

	// ===============================================================================================
	// Day 19 - I thought I did this to get away from incremental games
	// ===============================================================================================

	private List<int[][]> blueprints;
	private int maxGeodes, output;

	@Override
	public Object exec(AdventOfCode aoc) {
		blueprints = new ArrayList<>();
		output = 1;
		aoc.inputLst().forEach(this::constructBlueprint);
		findBlueprintQualities();
		return output;
	}


	// ===============================================================================================
	// Blueprint is a 4x3 array with each row showing resources required to make robot.
	// ===============================================================================================

	private void constructBlueprint(String line) {
		int[] bp = parseIntStream(line.split("\\D+")).toArray();
		blueprints.add(
				new int[][]{
						new int[]{bp[1], 0, 0},
						new int[]{bp[2], 0, 0},
						new int[]{bp[3], bp[4], 0},
						new int[]{bp[5], 0, bp[6]},
				});
	}

	// ===============================================================================================
	// Algorithm : Resources and robots are tracked as [ore, clay, obsidian, geode]
	// ===============================================================================================

	private void findBlueprintQualities() {
		for (int i = 0; i < 3; i++) {
			findQuality(blueprints.get(i), new int[4], new int[]{1, 0, 0, 0}, 32);
			output *= maxGeodes;
			maxGeodes = 0;
		}
	}

	private int findQuality(int[][] blueprint, int[] resources, int[] robots, int time) {
		if ((time * time - time) / 2 + robots[3] * time <= maxGeodes - resources[3])
			return 0;
		int wait, max = resources[3] + robots[3] * time;
		int[] resourceCopy, robotCopy;

		for (int i = 0; i < 4; i++) {
			wait = timeForResources(blueprint[i], resources, robots) + 1;
			if (time - wait < 1)
				continue;

			resourceCopy = resources.clone();
			robotCopy = robots.clone();

			for (int j = 0; j < 4; j++)
				resourceCopy[j] += robots[j] * wait;
			for (int j = 0; j < 3; j++)
				resourceCopy[j] -= blueprint[i][j];

			robotCopy[i]++;

			max = Math.max(max, findQuality(blueprint, resourceCopy, robotCopy, time - wait));
		}

		maxGeodes = Math.max(maxGeodes, max);
		return max;
	}

	private int timeForResources(int[] cost, int[] resources, int[] robots) {
		int maxTime = 0;
		for (int i = 0; i < 3; i++) {
			if (cost[i] == 0)
				continue;
			if (cost[i] > 0 && robots[i] == 0)
				return Integer.MAX_VALUE;
			maxTime = Math.max(maxTime, (cost[i] - resources[i] - 1 + robots[i]) / robots[i]);
		}
		return maxTime;
	}
}
