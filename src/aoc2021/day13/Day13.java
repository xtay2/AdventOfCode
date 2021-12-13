package aoc2021.day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.lang.Math.*;

public class Day13 {

	public static void main(String[] args) throws IOException {
		ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("input")));
		System.out.println("Points after first fold: " + task1(lines));
		System.out.println("Code when folded: ");
		task2(lines);
	}

	static long task1(ArrayList<String> input) {
		boolean[][] paper = initPaper(input);
		for (String e : input) {
			if (e.isBlank())
				continue;
			if (e.startsWith("fold along x")) {
				paper = foldAlongX(paper, txt2nr(e.split("=")[1]));
				break;
			} else
				paper[txt2nr(e.split(",")[0])][txt2nr(e.split(",")[1])] = true;
		}
		long cnt = 0;
		for (int x = 0; x < paper.length; x++) {
			for (int y = 0; y < paper[0].length; y++)
				cnt += paper[x][y] ? 1 : 0;
		}
		return cnt;
	}

	static void task2(ArrayList<String> input) {
		boolean[][] paper = initPaper(input);
		for (String e : input) {
			if (e.isBlank())
				continue;
			if (e.startsWith("fold along x"))
				paper = foldAlongX(paper, txt2nr(e.split("=")[1]));
			else if (e.startsWith("fold along y"))
				paper = foldAlongY(paper, txt2nr(e.split("=")[1]));
			else
				paper[txt2nr(e.split(",")[0])][txt2nr(e.split(",")[1])] = true;
		}
		printPaper(paper);
	}

	static boolean[][] initPaper(ArrayList<String> input) {
		int maxX = 0, maxY = 0;
		for (String e : input) {
			if (e.isBlank())
				break;
			maxX = max(txt2nr(e.split(",")[0]), maxX);
			maxY = max(txt2nr(e.split(",")[1]), maxY);
		}
		return new boolean[maxX + 1][maxY + 1];
	}

	static int txt2nr(String s) {
		return Integer.valueOf(s);
	}

	static boolean[][] foldAlongX(boolean[][] paper, int fold) {
		boolean[][] folded = new boolean[fold][paper[0].length];
		for (int x = 0; x < folded.length; x++) {
			for (int y = 0; y < folded[0].length; y++)
				folded[x][y] = paper[x][y] || paper[paper.length - 1 - x][y];
		}
		return folded;
	}

	static boolean[][] foldAlongY(boolean[][] paper, int fold) {
		boolean[][] folded = new boolean[paper.length][fold];
		for (int x = 0; x < folded.length; x++) {
			for (int y = 0; y < folded[0].length; y++)
				folded[x][y] = paper[x][y] || paper[x][paper[0].length - 1 - y];
		}
		return folded;
	}

	static void printPaper(boolean[][] m) {
		for (int i = 0; i < m[0].length; i++) {
			String output = "";
			for (int j = 0; j < m.length; j++) {
				output += j % (m.length / 8) == 0 ? "  " : "";
				output += (m[j][i] ? "#" : " ");
			}
			System.out.println(output.strip());
		}
	}
}