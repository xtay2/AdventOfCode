package helper;

import java.util.ArrayList;
import java.util.List;

public abstract class Task {

	private static final List<Task> TASKS = new ArrayList<>();
	public final AdventOfCode aoc;

	protected Task() {
		TASKS.add(this);
		aoc = new AdventOfCode(getClass());
	}

	protected abstract Object exec(AdventOfCode aoc);

	public static void main(String[] args) {
		var latest = TASKS.get(TASKS.size() - 1);
		System.out.println(latest.exec(latest.aoc));
	}

}
