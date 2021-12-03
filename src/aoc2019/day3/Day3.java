package aoc2019.day3;

import files.FileManager;

public class Day3 {
	public static void main(String[] args) {
		String[] strings = FileManager.readFile("input").split(",");
		char[][] map = new char[20000][20000];

		int x = 10000;
		int y = 10000;

		for (String s : strings) {
			switch (s.charAt(0)) {
			case 'U':
				for (int i = 0; i < Integer.valueOf(s.substring(1)); i++) {
					if (map[x][y] == '-')
						map[x][y] = '+';
					else
						map[x][y] = '|';
					y++;
				}
				break;
			case 'R':
				for (int i = 0; i < Integer.valueOf(s.substring(1)); i++) {
					if (map[x][y] == '|')
						map[x][y] = '+';
					else
						map[x][y] = '-';
					x++;
				}
				break;
			case 'L':
				for (int i = 0; i < Integer.valueOf(s.substring(1)); i++) {
					if (map[x][y] == '|')
						map[x][y] = '+';
					else
						map[x][y] = '-';
					x--;
				}
				break;
			case 'D':
				for (int i = 0; i < Integer.valueOf(s.substring(1)); i++) {
					if (map[x][y] == '-')
						map[x][y] = '+';
					else
						map[x][y] = '|';
					y--;
				}
				break;
			}
		}
		printMatrix(map);
	}

	private static void printMatrix(char[][] m) {
		String output = "";
		int dim = m.length;
		int longestOut[] = new int[dim];
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (String.valueOf(m[j][i]).length() > longestOut[j]) {
					longestOut[j] = String.valueOf(m[j][i]).length();
				}
			}
		}
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				String val = m[j][i] + "";
				output += "[";
				for (int k = 0; k < longestOut[j] - String.valueOf(val).length(); k++) {
					output += " ";
				}
				output += val + "]";
			}
			output += "\n";
		}
		System.out.println(output);
	}
}
