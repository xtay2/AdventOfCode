package year2023.day04;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.ArrayList;
import java.util.List;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        return aoc.inputStr()
                .mapToInt(line -> {
                    var colonSplit = line.split(":");
                    var pipeSplit = colonSplit[1].split("\\|");
                    var winningNrs = new ArrayList<>(List.of(pipeSplit[0].split("\\s+")));
                    var myNrs = new ArrayList<>(List.of(pipeSplit[1].split("\\s+")));
                    winningNrs.removeIf(String::isBlank);
                    myNrs.removeIf(String::isBlank);
                    System.out.println(winningNrs + " " + myNrs);
                    int sum = 0;
                    for (var myNr : myNrs) {
                        if (winningNrs.contains(myNr)) {
                            System.out.println(colonSplit[0] + " -> " + myNr);
                            sum = sum == 0 ? 1 : sum * 2;
                        }
                    }
                    return sum;
                })
                .sum();
    }
}