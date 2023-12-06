package year2023.day04;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    private static int[] cache;

    @Override
    protected Object exec(AdventOfCode aoc) {
        var arr = aoc.inputArr();
        cache = new int[arr.length];
        Arrays.fill(cache, -1);
        var sum = 0;
        for (int i = 0; i < arr.length; i++)
            sum += countForIdx(i, arr);
        return sum;
    }

    public int countForIdx(int index, String[] lines) {
        if (cache[index] != -1)
            return cache[index];
        var followers = 0;
        var line = lines[index];
        var colonSplit = line.split(":");
        var pipeSplit = colonSplit[1].split("\\|");
        var winningNrs = List.of(pipeSplit[0].split(" "));
        var pickedNrs = pipeSplit[1].split(" ");
        for (var pickedNr : pickedNrs) {
            if (!pickedNr.isBlank() && winningNrs.contains(pickedNr)) {
                followers++;
            }
        }
        var res = 1 + IntStream.range(index + 1, index + 1 + followers)
                .map(followerIdx -> countForIdx(followerIdx, lines))
                .sum();
        cache[index] = res;
        return res;
    }
}