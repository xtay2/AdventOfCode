package aoc2021.day12;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day12 {

	public static void main(String[] args) throws Exception {
		ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("input")));
		System.out.println("Dfs: " + task1(lines));
		System.out.println("Dfs with double visits: " + task2(lines));
	}

	static long task1(ArrayList<String> input) {
		return countPaths(new ArrayList<String>(List.of("start")), buildCavesystem(input), false);
	}

	static long task2(ArrayList<String> input) {
		return countPaths(new ArrayList<String>(List.of("start")), buildCavesystem(input), true);
	}

	static HashMap<String, ArrayList<String>> buildCavesystem(ArrayList<String> input) {
		HashMap<String, ArrayList<String>> caves = new HashMap<String, ArrayList<String>>();
		for (String line : input) {
			String[] cave = line.split("-");
			if (!caves.containsKey(cave[0]))
				caves.put(cave[0], new ArrayList<String>());
			if (!caves.containsKey(cave[1]))
				caves.put(cave[1], new ArrayList<String>());
			caves.get(cave[0]).add(cave[1]);
			caves.get(cave[1]).add(cave[0]);
		}
		return caves;
	}

	static long countPaths(ArrayList<String> path, HashMap<String, ArrayList<String>> caves, boolean visitTwice) {
		if (path.get(path.size() - 1).equals("end"))
			return 1;
		long cnt = 0;
		for (String neighbour : caves.get(path.get(path.size() - 1))) {
			if (neighbour.toUpperCase().equals(neighbour) || !path.contains(neighbour)) {
				path.add(neighbour);
				cnt += countPaths(path, caves, visitTwice);
				path.remove(path.size() - 1);
			} else if (visitTwice && !neighbour.equals("start") && !neighbour.equals("end")) {
				path.add(neighbour);
				cnt += countPaths(path, caves, false);
				path.remove(path.size() - 1);
			}
		}
		return cnt;
	}
}