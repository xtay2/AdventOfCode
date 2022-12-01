package year2022.day01;

import aoc.AdventOfCode;
import aoc.Task;
import helper.util.Generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Task_B extends Task {

	static {
		new Task_B();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		return new Generator<>(aoc.inputTxt())
				.map(f -> new Generator<>(f.split("\n\n"))    // Teile String in Gruppen
						.map(v -> new Generator<>(v.split("\n"))    // Teile Gruppen in Einträge
								.map(Integer::parseInt)    // Konvertiere Einträge zu Zahlen
								.reduce(0, Integer::sum))    // Berechne die Summe der Einträge jeder Gruppe
				)
				.flatten()
				.stream()
				.map(i -> ((Integer) i))
				.sorted(Comparator.reverseOrder())    // Sortiere absteigend
				.mapToInt(i -> i)
				.limit(3)    // Nimm die ersten 3
				.sum();    // Summiere
	}
}

