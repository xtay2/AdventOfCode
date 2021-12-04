package aoc2021.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.*;

public class Day4 {

	static int n;

	public static void main(String[] args) throws IOException {
		ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("input")));

		System.out.println("Task 1: " + task1(lines) + "\n");
		System.out.println("Task 2: " + task2(lines));
	}

	static int task1(ArrayList<String> lines) {
		n = lines.size();
		ArrayList<Board> boards = new ArrayList<>();
		ArrayList<String> inputs = new ArrayList<String>(List.of(lines.get(0).split(",")));

		for (int i = 2; i + Board.N < n; i += Board.N) {
			if (lines.get(i).isEmpty())
				i++;
			boards.add(new Board());
			for (int j = 0; j < Board.N; j++) {
				String line = lines.get(i + j);
				boards.get(boards.size() - 1).setRow(txt2Arr(line, " "), j);
			}
		}
		for (int i = 0; i < inputs.size(); i++) {
			for (Board b : boards) {
				b.mark(txt2nr(inputs.get(i)));
				if (b.hasBingo()) {
					b.printBoard();
					return b.getUnmarked() * txt2nr(inputs.get(i));
				}
			}
		}

		return -1;
	}

	static int task2(ArrayList<String> lines) {
		n = lines.size();
		ArrayList<Board> boards = new ArrayList<>();
		ArrayList<String> inputs = new ArrayList<String>(List.of(lines.get(0).split(",")));

		for (int i = 2; i + Board.N < n; i += Board.N) {
			if (lines.get(i).isEmpty())
				i++;
			boards.add(new Board());
			for (int j = 0; j < Board.N; j++) {
				String line = lines.get(i + j);
				boards.get(boards.size() - 1).setRow(txt2Arr(line, " "), j);
			}
		}
		for (int i = 0; i < inputs.size(); i++) {
			for (int j = 0; j < boards.size(); j++) {
				Board b = boards.get(j);
				b.mark(txt2nr(inputs.get(i)));
				if (b.hasBingo()) {
					boards.remove(j);
					j--;
					if (boards.size() == 0) {
						b.printBoard();
						return b.getUnmarked() * txt2nr(inputs.get(i));
					}
				}
			}
		}

		return -1;
	}

	static int txt2nr(String s) {
		return Integer.valueOf(s);
	}

	static int[] txt2Arr(String s, String regex) {
		ArrayList<String> su = new ArrayList<>(List.of(s.split(regex)));
		int[] res = new int[Board.N];
		for (int i = 0; i < su.size(); i++) {
			if (su.get(i).isBlank()) {
				su.remove(i);
				i--;
			}
		}
		for (int i = 0; i < Board.N; i++) {
			res[i] = Integer.valueOf(su.get(i).strip());
		}
		return res;
	}

}

class Board {

	public static final int N = 5;

	int[][] board = new int[N][N];
	boolean[][] markedBoard = new boolean[N][N];

	void setRow(int[] row, int index) {
		board[index] = row;
	}

	int getUnmarked() {
		int cnt = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (markedBoard[i][j] == false)
					cnt += board[i][j];
			}
		}
		return cnt;
	}

	void mark(int nr) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] == nr) {
					markedBoard[i][j] = true;
					return;
				}
			}
		}
	}

	boolean hasBingo() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (markedBoard[j][i] == false)
					break;
				if (j == N - 1)
					return true;
			}
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (markedBoard[i][j] == false)
					break;
				if (j == N - 1)
					return true;
			}
		}
		return false;
	}

	// Die hatte ich noch rumliegen.
	public void printBoard() {
		int[][] m = board;
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
