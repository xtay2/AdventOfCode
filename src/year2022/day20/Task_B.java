package year2022.day20;


import aoc.AdventOfCode;
import aoc.Task;
import helper.util.StreamHelper;
import helper.util.datastructures.tuples.points.LongPoint;

import java.util.List;
import java.util.stream.Collectors;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        List<LongPoint> inp = StreamHelper.mapIndexed(aoc.inputStr(),
                        (str, idx) -> new LongPoint(Integer.parseInt(str) * 811589153L, idx))
                .collect(Collectors.toList());
        int n = inp.size();
        for (int rep = 0; rep < 10; rep++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (inp.get(j).y == i) {
                        long val = inp.get(j).x;
                        inp.remove(j);
                        j = (int) (j + val) % (n - 1);
                        inp.add(j, new LongPoint(val, i));
                    }
                }
            }
        }

        final int[] nums = {1000, 2000, 3000};
        int sum = 0;
        for (int z = 0; z < n; z++) {
            if (inp.get(z).x == 0) {
                for (int num : nums) {
                    sum += inp.get((z + num) % n).x;
                }
            }
        }
        return sum;
    }
}