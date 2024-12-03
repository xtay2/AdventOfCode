package year2024.day03;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.regex.Pattern;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        Pattern p = Pattern.compile("mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)");
        var res = p.matcher(aoc.inputTxt())
                .results()
                .toList();
        boolean doExec = true;
        int sum = 0;
        for (var inst : res) {
            if (inst.group().equals("do()"))
                doExec = true;
            else if (inst.group().equals("don't()"))
                doExec = false;
            else if (doExec && inst.group().startsWith("mul"))
                sum += Integer.parseInt(inst.group(1)) * Integer.parseInt(inst.group(2));
        }
        return sum;
    }

}