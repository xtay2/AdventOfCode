package year2024.day09;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Task_B extends Task {

    static final int FREE_MEM = -1;

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var flattened = flatten(aoc.inputInts());
        compact(flattened);
        return calcChecksum(flattened);
    }

    int[] flatten(int[] ints) {
        int[] flattened = new int[IntStream.of(ints).sum()];
        int segIdx = 0;
        int fileId = 0;
        for (int i = 0; i < ints.length; i++) {
            int[] segment = new int[ints[i]];
            Arrays.fill(segment, i % 2 == 0 ? fileId++ : FREE_MEM);
            System.arraycopy(segment, 0, flattened, segIdx, segment.length);
            segIdx += segment.length;
        }
        return flattened;
    }


    void compact(int[] mem) {
        for (int fileEnd = mem.length - 1; fileEnd >= 0; fileEnd--) {
            var fileId = mem[fileEnd];
            if (fileId == FREE_MEM)
                continue;
            var fileSize = fileSize(mem, fileEnd);
            int freeIdx = freeSpace(mem, fileSize, fileEnd - fileSize);
            if (freeIdx == -1) {
                fileEnd -= fileSize - 1;
                continue;
            }
            Arrays.fill(mem, freeIdx, freeIdx + fileSize, fileId);
            Arrays.fill(mem, fileEnd - fileSize + 1, fileEnd + 1, FREE_MEM);
        }
    }


    int fileSize(int[] mem, int fileEnd) {
        int fileSize = 0;
        int fileId = mem[fileEnd];
        for (int i = fileEnd; i >= 0 && mem[i] == fileId; i--)
            fileSize++;
        return fileSize;
    }

    int freeSpace(int[] mem, int size, int rightBound) {
        for (int i = 0; i <= rightBound; i++) {
            int space = 0;
            while (i + space < mem.length && mem[i + space] == FREE_MEM) space++;
            if (space >= size) return i;
        }
        return -1;
    }

    long calcChecksum(int[] arr) {
        long checkSum = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != FREE_MEM)
                checkSum += (long) i * arr[i];
        }
        return checkSum;
    }

}