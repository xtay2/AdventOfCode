package aoc2021.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class Day8 {

	static final ArrayList<Line> LINE = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("input")));
		for (String l : lines) // Convert Strings to Lines
			LINE.add(new Line(l));
		System.out.println("Summe 1, 4, 8: " + task1());
		System.out.println("Encoded Output: " + task2());
	}

	static long task1() {
		int cnt = 0;
		for (Line line : LINE) {
			for (String s : line.output) {
				if (s.length() == 2 || s.length() == 3 || s.length() == 4 || s.length() == 7)
					cnt++;
			}
		}
		return cnt;
	}

	static long task2() {
		int sum = 0;
		for (Line line : LINE) {
			int temp = 0;
			for (String s : line.output)
				temp = temp * 10 + line.pattern.map.get(s); // Append newest digit.
			sum += temp;
		}
		return sum;
	}

}

class Line {

	final ArrayList<String> input = new ArrayList<>();
	final ArrayList<String> output = new ArrayList<>();
	final Pattern pattern;

	public Line(String line) {
		String[] lineSplit = line.split("\\|");

		// Split and sort
		for (String s : lineSplit[0].strip().split(" "))
			input.add(Line.sortString(s));
		for (String s : lineSplit[1].strip().split(" "))
			output.add(Line.sortString(s));

		pattern = new Pattern(input);
	}

	private static String sortString(String s) {
		char[] charArray = s.toCharArray();
		Arrays.sort(charArray);
		return String.valueOf(charArray);
	}
}

/**
 * <pre>
 * Sortiere jede Zeile nach Stringlänge und leite ein Muster aus den Indizes ab.
 * index	digit	length
 *     0		1		 2
 *     1		7		 3
 *     2		4		 4
 *   3-5	2|3|5 		 5 //Variabel
 *   6-8	0|6|9		 6 //Variabel
 *     9		8		 7
 * </pre>
 */
class Pattern {

	final HashMap<String, Integer> map = new HashMap<>();
	final ArrayList<String> entries;

	public Pattern(ArrayList<String> s) {
		this.entries = s;

		// Sortiere nach Stringlänge
		entries.sort(Comparator.comparingInt(String::length));

		map.put(entries.get(0), 1);
		map.put(entries.get(1), 7);
		map.put(entries.get(2), 4);
		map.put(entries.get(9), 8);

		// Index der 6 wird gespeichert, da er für die 5 gebraucht wird.
		int sixIndex = 0;

		/**
		 * <pre>
		 * Überprüfe alle Werte mit Länge 6, ie: 0, 9 und 6
		 * 
		 * 9 besteht aus 1, 7 und 4 			  //Index 0, 1, 2
		 * 0 besteht nur aus 1 und 7			  //Index 0, 1
		 * 6 besteht weder aus 1, noch 7, noch 4
		 * </pre>
		 */
		for (int i = 6; i <= 8; i++) {
			String d = entries.get(i);
			if (matches(i, 0) && matches(i, 1) && matches(i, 2))
				map.put(d, 9);
			else if (matches(i, 0) && matches(i, 1))
				map.put(d, 0);
			else {
				map.put(d, 6);
				sixIndex = i;
			}
		}

		/**
		 * Überprüfe die restlichen Werte: 3, 2 und 5
		 * 
		 * 3 besteht aus 1 und 7
		 * 
		 * 5 besteht aus 6
		 * 
		 * 2 besteht weder aus 1, noch 7, noch 6
		 */
		for (int i = 3; i <= 5; i++) {
			String d = entries.get(i);
			if (matches(i, 0) && matches(i, 1))
				map.put(d, 3);
			else if (matches(sixIndex, i))
				map.put(d, 5);
			else
				map.put(d, 2);
		}
	}

	/** Sind alle chars aus s irgendwo in t? */
	private boolean matches(int i, int j) {
		for (char c : entries.get(j).toCharArray()) {
			if (entries.get(i).indexOf(c) == -1)
				return false;
		}
		return true;
	}
}
