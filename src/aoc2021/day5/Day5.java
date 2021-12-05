package aoc2021.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.lang.Math.*;

public class Day5 {

	/** Linecount */
	static int n;

	public static void main(String[] args) throws IOException {
		ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("input")));
		System.out.println("Only Straight Lines: " + task1(lines));
		System.out.println("Straight & Diagonal: " + task2(lines));
	}

	static int task1(ArrayList<String> input) {
		n = input.size();
		Line[] lines = initLines(input);
		int[][] diagram = new int[1000][1000];
		for (Line l : lines) {
			if (l.y1 == l.y2) { // Vertikal
				for (int j = min(l.x1, l.x2); j <= max(l.x1, l.x2); j++)
					diagram[j][l.y1]++;
			}
			if (l.x1 == l.x2) {// Horizontal
				for (int j = min(l.y1, l.y2); j <= max(l.y1, l.y2); j++)
					diagram[l.x1][j]++;
			}
		}
		return overlapping(diagram);
	}

	static int task2(ArrayList<String> input) {
		n = input.size();
		Line[] lines = initLines(input);
		int[][] diagram = new int[1000][1000];
		for (Line l : lines) {
			if (l.y1 == l.y2) {// Vertikal
				for (int j = min(l.x1, l.x2); j <= max(l.x1, l.x2); j++)
					diagram[j][l.y1]++;
			} else if (l.x1 == l.x2) {// Horizontal
				for (int j = min(l.y1, l.y2); j <= max(l.y1, l.y2); j++)
					diagram[l.x1][j]++;
			} else {// Diagonal
				if (l.x1 + l.y1 == l.x2 + l.y2) { // Links unten -> Rechts oben /
					for (int j = min(l.x1, l.x2), k = max(l.y1, l.y2); j <= max(l.x1, l.x2) && k >= min(l.y1, l.y2); j++, k--)
						diagram[j][k]++;
				} else {// Links oben -> Rechts unten \
					for (int j = min(l.x1, l.x2), k = min(l.y1, l.y2); j <= max(l.x1, l.x2) && k <= max(l.y1, l.y2); j++, k++)
						diagram[j][k]++;
				}
			}
		}
		return overlapping(diagram);
	}

	private static Line[] initLines(ArrayList<String> input) {
		Line[] lines = new Line[n];
		for (int i = 0; i < n; i++) {
			String line = input.get(i);
			String[] tp = line.split(" -> ");
			lines[i] = new Line( // Init Line
					txt2nr(tp[0].split(",")[0]), // x, ... -> ..., ...
					txt2nr(tp[0].split(",")[1]), // ..., x -> ..., ...
					txt2nr(tp[1].split(",")[0]), // ..., ... -> x, ...
					txt2nr(tp[1].split(",")[1]) /// ..., ... -> ..., x
			);
		}
		return lines;
	}

	private static int overlapping(int[][] diagram) {
		int overlapping = 0;
		for (int i = 0; i < diagram.length; i++) {
			for (int j = 0; j < diagram.length; j++) {
				if (diagram[i][j] >= 2)
					overlapping++;
			}
		}
		return overlapping;
	}

	static int txt2nr(String s) {
		return Integer.valueOf(s);
	}

}

class Line {
	public int x1, x2, y1, y2;

	public Line(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
}
