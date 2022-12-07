package year2022.day07;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Task_B extends Task {

	static {
		new Task_B();
	}

	@Override
	protected Object exec(AdventOfCode aoc) {
		final var root = new Directory("/", null);
		var current = root;
		for (var line : aoc.inputLst()) {
			if (line.startsWith("$")) {
				if (line.startsWith("$ cd")) {
					if (line.startsWith("$ cd .."))
						current = current.parent;
					else if (line.startsWith("$ cd /"))
						current = root;
					else {
						var dirName = line.substring(5);
						var dirIdx = current.files.indexOf(dirName);
						Directory newDir;
						if (dirIdx == -1) {
							newDir = new Directory(dirName, current);
							current.files.add(newDir);
						} else
							newDir = (Directory) current.files.get(dirIdx);
						current = newDir;
					}
				}
			} else {
				if (line.startsWith("dir"))
					current.files.add(new Directory(line.split(" ")[1], current));
				else {
					var info = line.split(" ");
					current.files.add(new File(info[1], current, Long.parseLong(info[0])));
				}
			}
		}
		var unused = 30000000 - (70000000 - root.size());
		return Directory.dirSizes.stream().mapToLong(s -> s)
				.filter(e -> unused <= e)
				.min().orElse(-1);
	}

	abstract static class Content {

		final String name;
		final Directory parent;

		Content(String name, Directory parent) {
			this.name = name.strip();
			this.parent = parent;
		}

		@Override
		public boolean equals(Object obj) {
			return obj == this || obj instanceof Content c
					&& Objects.equals(c.parent, parent) && Objects.equals(c.name, name);
		}

		abstract long size();

	}

	static final class File extends Content {
		final long size;

		File(String name, Directory parent, long size) {
			super(name, parent);
			this.size = size;
		}

		@Override
		public long size() {return size;}

	}


	static class Directory extends Content {

		static List<Long> dirSizes = new ArrayList<>();

		final List<Content> files = new ArrayList<>();

		Directory(String name, Directory parent) {
			super(name, parent);
		}

		@Override
		public long size() {
			var size = files.stream().mapToLong(Content::size).sum();
			dirSizes.add(size);
			return size;
		}
	}
}
