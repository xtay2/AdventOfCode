package aoc2021.day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day4 {

	static int n;

	public static void main(String[] args) throws IOException {
		ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("input")));
		System.out.println("First Winner Sum: " + task1(lines) + "\n");
		System.out.println("Last Winner Sum: " + task2(lines));
	}

	static int task1(ArrayList<String> lines) {
		n = lines.size();
		ArrayList<String> inputs = new ArrayList<String>(List.of(lines.get(0).split(",")));
		ArrayList<Board> boards = buildBoards(lines);
		// Play games
		for (int i = 0; i < inputs.size(); i++) {
			for (Board b : boards) {
				if (b.mark(txt2nr(inputs.get(i))))
					return b.getUnmarkedSum() * txt2nr(inputs.get(i));
			}
		}
		throw new AssertionError("There should be a valid solution by now.");
	}

	static int task2(ArrayList<String> lines) {
		n = lines.size();
		ArrayList<String> inputs = new ArrayList<String>(List.of(lines.get(0).split(",")));
		ArrayList<Board> boards = buildBoards(lines);
		// Play games
		for (int i = 0; i < inputs.size(); i++) {
			for (int j = 0; j < boards.size(); j++) {
				Board b = boards.get(j);
				if (b.mark(txt2nr(inputs.get(i)))) {
					boards.remove(j);
					j--;
					if (boards.size() == 0)
						return b.getUnmarkedSum() * txt2nr(inputs.get(i));
				}
			}
		}
		throw new AssertionError("There should be a valid solution by now.");
	}

	static ArrayList<Board> buildBoards(ArrayList<String> lines) {
		ArrayList<Board> boards = new ArrayList<>();
		for (int i = 2; i + Board.N < n; i += Board.N) {
			if (lines.get(i).isEmpty())
				i++;
			boards.add(new Board());
			for (int j = 0; j < Board.N; j++) {
				String line = lines.get(i + j);
				boards.get(boards.size() - 1).initRow(txt2intArr(line.strip().split(" +")), j);
			}
		}
		return boards;
	}

	private static int[] txt2intArr(String[] line) {
		int[] res = new int[line.length];
		for (int i = 0; i < line.length; i++)
			res[i] = txt2nr(line[i]);
		return res;
	}

	static int txt2nr(String s) {
		return Integer.valueOf(s);
	}
}

class Board {

	public static final int N = 5;

	private int[][] board = new int[N][N];
	private boolean[][] markedBoard = new boolean[N][N];

	void initRow(int[] row, int index) {
		board[index] = row;
	}

	int getUnmarkedSum() {
		int cnt = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (markedBoard[i][j] == false)
					cnt += board[i][j];
			}
		}
		return cnt;
	}

	boolean mark(int nr) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] == nr) {
					markedBoard[i][j] = true;
					return isBingo(i, j);
				}
			}
		}
		return false;
	}

	private boolean isBingo(int x, int y) {
		int row = 0, col = 0;
		for (int i = 0; i < N; i++) {
			row += markedBoard[x][i] ? 1 : 0;
			col += markedBoard[i][y] ? 1 : 0;
		}
		return row == 5 || col == 5;
	}
}
