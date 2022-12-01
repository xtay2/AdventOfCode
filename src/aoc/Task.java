package aoc;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;

public abstract class Task implements Comparable<Task> {

	private static final List<Task> TASKS = new ArrayList<>();
	public final AdventOfCode aoc;

	protected Task() {
		TASKS.add(this);
		aoc = new AdventOfCode(getClass());
		System.out.println("Loaded: " + this);
	}

	@Override
	public final int compareTo(Task o) {
		return toString().compareTo(o.toString());
	}

	protected abstract Object exec(AdventOfCode aoc);

	public static void main(String[] args) {
		if (!TASKS.isEmpty()) {
			var latest = TASKS.get(TASKS.size() - 1);
			var solution = latest.exec(latest.aoc).toString();

			// Copy solution to clipboard
			StringSelection selection = new StringSelection(solution);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, selection);

			System.out.println("Output: " + solution);
			System.out.println("Solution was copied to clipboard.");
		} else
			System.err.println("No task was defined.");
	}

	@Override
	public String toString() {
		var name = getClass().getSimpleName();
		return aoc.year + "/" + aoc.day + "/" + name.charAt(name.length() - 1);
	}
}
