package aoc2021.day11;

import files.FileManager;

/**
 * Es ist ein wundeschöner Tag für unnötig viele verschachtelte for-Schleifen :)
 */
public class Day11 {

	static int[][] octoAt;
	static boolean[][] hasFlashed;

	public static void main(String[] args) {
		System.out.println("Sum on flashes: " + task1());
		System.out.println("First sync on day: " + task2());
	}

	static long flashes = 0;

	static long task1() {
		octoAt = buildMatrix(FileManager.fileToLineArray("input"));
		hasFlashed = new boolean[octoAt.length][octoAt[0].length];
		for (int i = 0; i < 100; i++)
			simulateDay(i, false);
		return flashes;
	}

	static int task2() {
		octoAt = buildMatrix(FileManager.fileToLineArray("input"));
		hasFlashed = new boolean[octoAt.length][octoAt[0].length];
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			if (simulateDay(i, true) != -1)
				return i;
		}
		throw new AssertionError("There was no synchronization.");
	}

	static int simulateDay(int day, boolean checkForSynch) {
		// Increment everything
		for (int i = 0; i < octoAt.length; i++) {
			for (int j = 0; j < octoAt[0].length; j++)
				octoAt[i][j]++;
		}
		// Initial flashes
		for (int i = 0; i < octoAt.length; i++) {
			for (int j = 0; j < octoAt[0].length; j++)
				tryToFlash(i, j);
		}

		// Check if synced. (Task2)
		if (checkForSynch && checkIfSynched())
			return day;

		// Reset flashed
		for (int i = 0; i < octoAt.length; i++) {
			for (int j = 0; j < octoAt[0].length; j++) {
				if (hasFlashed[i][j]) {
					hasFlashed[i][j] = false;
					octoAt[i][j] = 0;
				}
			}
		}
		return -1; // Unsynced Day or checkForSynched == false
	}

	static boolean checkIfSynched() {
		int sugg = octoAt[0][0];
		for (int i = 0; i < octoAt.length; i++) {
			for (int j = 0; j < octoAt[0].length; j++) {
				if (octoAt[i][j] != sugg)
					return false;
			}
		}
		return true;
	}

	static void increaseNeighbours(int x, int y) {
		// ADJACENT
		if (x - 1 >= 0)
			getsFlashed(x - 1, y);
		if (x + 1 < octoAt.length)
			getsFlashed(x + 1, y);
		if (y - 1 >= 0)
			getsFlashed(x, y - 1);
		if (y + 1 < octoAt[0].length)
			getsFlashed(x, y + 1);
		
		// DIAGONAL
		if (x - 1 >= 0 && y - 1 >= 0)
			getsFlashed(x - 1, y - 1);
		if (x - 1 >= 0 && y + 1 < octoAt[0].length)
			getsFlashed(x - 1, y + 1);
		if (x + 1 < octoAt.length && y - 1 >= 0)
			getsFlashed(x + 1, y - 1);
		if (x + 1 < octoAt.length && y + 1 < octoAt[0].length)
			getsFlashed(x + 1, y + 1);
	}

	static void getsFlashed(int x, int y) {
		octoAt[x][y]++;
		tryToFlash(x, y);
	}

	static void tryToFlash(int x, int y) {
		if (!hasFlashed[x][y] && octoAt[x][y] > 9) {
			flashes++;
			hasFlashed[x][y] = true;
			increaseNeighbours(x, y);
		}
	}

	static int[][] buildMatrix(String[] input) {
		int[][] m = new int[input[0].length()][input.length];
		for (int i = 0; i < input.length; i++) {
			String e = input[i];
			int[] row = txt2intArr(e.toCharArray());
			for (int j = 0; j < row.length; j++)
				m[j][i] = row[j];
		}
		return m;
	}

	static int[] txt2intArr(char[] cs) {
		int[] res = new int[cs.length];
		for (int i = 0; i < cs.length; i++)
			res[i] = Character.digit(cs[i], 10);
		return res;
	}

}
