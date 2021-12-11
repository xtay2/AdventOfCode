package aoc2021.day09;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import files.FileManager;

public class Day9 {

	static final int[][] MATRIX = buildMatrix(FileManager.fileToLineArray("input"));;

	public static void main(String[] args) {
		System.out.println("Sum of lowest risk-levels: " + task1());
		System.out.println("Product of basin-size: " + task2());
	}

	private static int[][] buildMatrix(String[] input) {
		int[][] m = new int[input[0].length()][input.length];
		for (int i = 0; i < input.length; i++) {
			String e = input[i];
			int[] row = txt2intArr(e.toCharArray());
			for (int j = 0; j < row.length; j++) {
				m[j][i] = row[j];
			}
		}
		return m;
	}

	static long task1() {
		ArrayList<Integer> lowpoints = new ArrayList<>();
		for (int i = 0; i < MATRIX.length; i++) {
			for (int j = 0; j < MATRIX[0].length; j++) {
				if (isLowPoint(i, j))
					lowpoints.add(MATRIX[i][j]);
			}
		}
		long sum = 0;
		for (int i : lowpoints)
			sum += i + 1;
		return sum;
	}
	
	
	static boolean isLowPoint(int x, int y) {
		int val = MATRIX[x][y];
		boolean res = true;
		if (x - 1 >= 0)
			res = res && (val < MATRIX[x - 1][y]);
		if (x + 1 < MATRIX.length)
			res = res && (val < MATRIX[x + 1][y]);
		if (y - 1 >= 0)
			res = res && (val < MATRIX[x][y - 1]);
		if (y + 1 < MATRIX[0].length)
			res = res && (val < MATRIX[x][y + 1]);
		return res;
	}

	static long task2() {
		ArrayList<Long> basins = new ArrayList<>();
		for (int i = 0; i < MATRIX.length; i++) {
			for (int j = 0; j < MATRIX[0].length; j++) {
				if (isLowPoint(i, j))
					basins.add(new Basin(MATRIX, i, j).size);
			}
		}
		Collections.sort(basins, Comparator.reverseOrder());
		return basins.get(0) * basins.get(1) * basins.get(2);
	}

	static int[] txt2intArr(char[] cs) {
		int[] res = new int[cs.length];
		for (int i = 0; i < cs.length; i++)
			res[i] = Character.digit(cs[i], 10);
		return res;
	}

}

final class Basin {

	final int[][] matrix;
	final boolean[][] searched;
	long size;

	public Basin(int[][] matrix, int lowptX, int lowptY) {
		this.matrix = matrix;
		searched = new boolean[matrix.length][matrix[0].length];
		findBasinSize(lowptX, lowptY);
	}

	private void findBasinSize(int x, int y) {
		searched[x][y] = true;
		if (matrix[x][y] == 9)
			return;
		size++;
		if (x - 1 >= 0 && !searched[x - 1][y])
			findBasinSize(x - 1, y);
		if (x + 1 < matrix.length && !searched[x + 1][y])
			findBasinSize(x + 1, y);
		if (y - 1 >= 0 && !searched[x][y - 1])
			findBasinSize(x, y - 1);
		if (y + 1 < matrix[0].length && !searched[x][y + 1])
			findBasinSize(x, y + 1);
	}

}