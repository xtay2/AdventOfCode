package year2022.day01;

import aoc.AdventOfCode;
import aoc.Task;
import helper.util.Generator;

public class Task_A extends Task {

	static {
		new Task_A();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		return new Generator<>(aoc.inputTxt())
				.map(f -> new Generator<>(f.split("\n\n"))                 // Teile String in Gruppen
						.map(v -> new Generator<>(v.split("\n"))   // Teile Gruppen in Einträge
								.map(Integer::parseInt)    // Konvertiere Einträge zu Zahlen
								.reduce(0, Integer::sum))  // Berechne die Summe der Einträge jeder Gruppe
				)
				.flatten()
				.stream()
				.mapToInt(i -> ((Integer) i))
				.max()                                           // Berechne Maximum aller Summen
				.getAsInt();                                     // Konvertiere Optional zu int
	}
}

