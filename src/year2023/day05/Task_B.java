package year2023.day05;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        Scanner s = new Scanner(aoc.inputTxt());
        long out = Long.MAX_VALUE, out2 = Long.MAX_VALUE;
        String l = s.nextLine();
        Scanner ss = new Scanner(l.substring(l.indexOf(':') + 2));
        var vals = new ArrayList<Long>();
        var vals2 = new ArrayList<long[]>();
        while (ss.hasNextLong()) vals.add(ss.nextLong());
        for (int i = 0; i < vals.size(); i += 2)
            vals2.add(new long[]{vals.get(i), vals.get(i) + vals.get(i + 1) - 1});
        ss.close();
        s.nextLine();
        while (s.hasNextLine()) {
            s.nextLine(); // skip map title
            var newVals = new ArrayList<>(vals);
            var mapped = new ArrayList<long[]>();
            while (s.hasNextLine() && (l = s.nextLine()).length() > 0) {
                ss = new Scanner(l);
                long[] L = new long[3];
                for (int i = 0; i < 3; i++) L[i] = ss.nextLong();
                ss.close();
                final long dest = L[0], src = L[1], len = L[2];
                for (int i = 0; i < vals.size(); i++) {
                    long v = vals.get(i);
                    if (v >= src && v < src + len) newVals.set(i, v + dest - src);
                }
                for (int i = 0; i < vals2.size(); i++) {
                    long start = vals2.get(i)[0], end = vals2.get(i)[1];
                    if (start < src + len && end >= src) { // ranges overlap
                        long[] m = {start + dest - src, end + dest - src};
                        boolean hasUnmapped = false;
                        if (start < src) { // split if input range starts before source range
                            m[0] = dest;
                            vals2.get(i)[0] = start;
                            vals2.get(i)[1] = src - 1;
                            hasUnmapped = true;
                        }
                        if (end >= src + len) { // split if input range ends after source range
                            m[1] = dest + len - 1;
                            if (hasUnmapped) vals2.add(new long[]{src + len, end});
                            else {
                                vals2.get(i)[0] = src + len;
                                vals2.get(i)[1] = end;
                                hasUnmapped = true;
                            }
                        }
                        mapped.add(m);
                        if (!hasUnmapped) vals2.remove(i--);
                    }
                }
            }
            vals = newVals;
            vals2.addAll(mapped);
        }
        s.close();
        for (long i : vals) if (i < out) out = i;
        for (long[] i : vals2) if (i[0] < out2) out2 = i[0];

        System.out.println(out2);
        return out2;
    }
}