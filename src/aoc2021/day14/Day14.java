
package aoc2021.day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day14 {

	public static void main(String[] args) throws IOException {
		ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("input")));
		System.out.println("Task 1: " + polymerize(lines.get(0), getMapping(lines), 10));
		System.out.println("Task 2: " + polymerize(lines.get(0), getMapping(lines), 40));
	}

	static HashMap<String, String> getMapping(ArrayList<String> input) {
		HashMap<String, String> mapping = new HashMap<>();
		for (String e : input) {
			if (e.isBlank())
				continue;
			if (e.contains("->"))
				mapping.put(e.split("->")[0].strip(), e.split("->")[1].strip());
		}
		return mapping;
	}

	static long polymerize(String in, final HashMap<String, String> mapping, int steps) {
		//Init
		HashMap<String, Long> tuples = new HashMap<>();
		HashMap<String, Long> occ = new HashMap<>();
		for (int i = 0; i < in.length() - 1; i++)
			addTo(tuples, in.substring(i, i + 2), 1);
		
		//N-Step-Iterations
		for (int i = 0; i < steps; i++) {
			HashMap<String, Long> np = new HashMap<>();
			for (var kv : tuples.entrySet()) {
				String pair = kv.getKey();
				long c = kv.getValue();
				String m = mapping.get(pair);
				String l = pair.substring(0, 1);
				String r = pair.substring(1, 2);
				addTo(np, l + m, c);
				addTo(np, m + r, c);
				addTo(occ, m, c);
			}
			tuples = np;
		}
		//Sort
		ArrayList<Long> quantity = new ArrayList<Long>(occ.values());
		Collections.sort(quantity);
		return quantity.get(quantity.size() - 1) - quantity.get(0) - 1;
	}

	static void addTo(HashMap<String, Long> map, String key, long val) {
		if (map.containsKey(key))
			map.replace(key, val + map.get(key));
		else
			map.put(key, val);
	}

}