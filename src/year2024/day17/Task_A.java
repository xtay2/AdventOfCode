package year2024.day17;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) throws Exception {
        var inputLines = aoc.inputLst();
        var regPattern = Pattern.compile("Register [A-C]: (\\d+)");
        var registers = inputLines.subList(0, 3)
                .stream()
                .map(regPattern::matcher)
                .peek(Matcher::find)
                .map(m -> Integer.parseInt(m.group(1)))
                .toArray(Integer[]::new);
        var program = Arrays.stream(inputLines.getLast().split(": ")[1].split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
        var output = new Program(registers[0], registers[1], registers[2], program).run();
        return output.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    static class Program {

        final int[] program;
        int instructionPointer = 0;
        int registerA;
        int registerB;
        int registerC;
        List<Integer> output = new ArrayList<>();

        Program(int registerA, int registerB, int registerC, int[] program) {
            this.registerA = registerA;
            this.registerB = registerB;
            this.registerC = registerC;
            this.program = program;
        }

        List<Integer> run() {
            while (instructionPointer < program.length - 1) {
                int opcode = program[instructionPointer];
                int operand = program[instructionPointer + 1];
                switch (opcode) {
                    case 0 -> adv(operand);
                    case 1 -> bxl(operand);
                    case 2 -> bst(operand);
                    case 3 -> jnz(operand);
                    case 4 -> bxc(operand);
                    case 5 -> out(operand);
                    case 6 -> bdv(operand);
                    case 7 -> cdv(operand);
                }
            }
            return Collections.unmodifiableList(output);
        }

        int comboOperand(int operand) {
            return switch (operand) {
                case 0, 1, 2, 3 -> operand;
                case 4 -> registerA;
                case 5 -> registerB;
                case 6 -> registerC;
                default -> throw new IllegalStateException("Unexpected value: " + operand);
            };
        }

        void adv(int operand) {
            registerA = registerA / (1 << comboOperand(operand));
            instructionPointer += 2;
        }

        void bxl(int operand) {
            registerB ^= operand;
            instructionPointer += 2;
        }

        void bst(int operand) {
            registerB = comboOperand(operand) % 8;
            instructionPointer += 2;
        }

        void jnz(int operand) {
            if (registerA != 0) instructionPointer = operand;
            else instructionPointer += 2;
        }

        void bxc(int operand) {
            registerB ^= registerC;
            instructionPointer += 2;
        }

        void out(int operand) {
            output.add(comboOperand(operand) % 8);
            instructionPointer += 2;
        }

        void bdv(int operand) {
            registerB = registerA / (1 << comboOperand(operand));
            instructionPointer += 2;
        }

        void cdv(int operand) {
            registerC = registerA / (1 << comboOperand(operand));
            instructionPointer += 2;
        }
    }

}