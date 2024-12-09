package year2024.day09;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Task_A extends Task {

    static final int FREE_MEM = -1;

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var ints = aoc.inputInts();
        ints = flatten(ints);
        compact(ints);
        return calcChecksum(ints);
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

    void compact(int[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            var fileId = arr[i];
            if (fileId == FREE_MEM)
                continue;
            int freeIdx = 0;
            while (arr[freeIdx] != FREE_MEM && freeIdx < i) freeIdx++;
            if (freeIdx > i) break;
            arr[i] = FREE_MEM;
            arr[freeIdx] = fileId;
        }
    }

    long calcChecksum(int[] arr) {
        long checkSum = 0;
        for (int i = 0; arr[i] != FREE_MEM; i++)
            checkSum += (long) i * arr[i];
        return checkSum;
    }

}