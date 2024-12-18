package aoc;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Task implements Comparable<Task> {

    private static final List<Task> TASKS = new ArrayList<>();
    public final AdventOfCode aoc;

    protected Task() {
        TASKS.add(this);
        aoc = new AdventOfCode(getClass());
        System.out.println("Loaded: " + this);
    }

    public static void main(String[] args) {
        if (!TASKS.isEmpty()) {
            var latest = TASKS.get(TASKS.size() - 1);
            try {
                var solution = Objects.toString(latest.exec(latest.aoc));
                // Copy solution to clipboard
                StringSelection selection = new StringSelection(solution);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);

                System.out.println("Output: " + solution);
                System.out.println("Solution was copied to clipboard.");
            } catch (Exception e) {
                System.err.println("An error occurred while executing the task.");
                e.printStackTrace();
            }
        } else
            System.err.println("No task was defined.");
    }

    @Override
    public final int compareTo(Task o) {
        return toString().compareTo(o.toString());
    }

    protected abstract Object exec(AdventOfCode aoc) throws Exception;

    @Override
    public String toString() {
        var name = getClass().getSimpleName();
        return aoc.year + "/" + aoc.day + "/" + name.charAt(name.length() - 1);
    }
}