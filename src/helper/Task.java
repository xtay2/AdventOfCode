package helper;

import java.util.ArrayList;
import java.util.List;

public abstract class Task implements Comparable<Task>{

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
		if(!TASKS.isEmpty()) {
			var latest = TASKS.get(TASKS.size() - 1);
			System.out.println("Output: " + latest.exec(latest.aoc));
		}else
			System.err.println("No task was defined.");
	}

	@Override
	public String toString() {
		var name = getClass().getSimpleName();
		return aoc.year +  "/" + aoc.day + "/" + name.charAt(name.length() -1);
	}
}
