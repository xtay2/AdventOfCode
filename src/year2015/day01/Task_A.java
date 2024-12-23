package year2015.day01;

import aoc.AdventOfCode;
import aoc.Task;

import static helper.base.text.StringHelper.occ;


public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var input = aoc.inputTxt();
        return occ('(', input) - occ(')', input);
    }
}