package aoc2021.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import static java.lang.Math.*;

public class Day4 {

	static int n;

	public static void main(String[] args) throws IOException {
		ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("input")));

		System.out.println("Task 1: " + task1(lines));
		System.out.println("Task 2: " + task1(lines));
	}

	static int task1(ArrayList<String> lines) {
		n = lines.size();
		for (int i = 0; i < n; i++) {
			String line = lines.get(i);

		}
		return 0;
	}

	static int task2(ArrayList<String> lines) {
		n = lines.size();
		for (int i = 0; i < n; i++) {
			String line = lines.get(i);

		}
		return 0;
	}

	static int txt2nr(String s) {
		return Integer.valueOf(s);
	}

	static int txt2nr(char s) {
		return Integer.valueOf(s);
	}

	static int nthChar2nr(String s, int index) {
		return txt2nr(s.charAt(index));
	}
}
