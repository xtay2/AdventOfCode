import java.util.ArrayList;

import files.FileManager;

public class Bla {

    public static int[][] input;

    public static void main(String[] args) {
        String[] read = FileManager.fileToLineArray("input");
        input = convert(read);
        System.out.println("task 1: " + task1());
        System.out.println("task 2: " + task2());
    }

    public static int task1() {
        ArrayList<Integer> mem = new ArrayList<>();
        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[0].length; x++) {
                if (!isThereLower(y, x))
                    mem.add(Integer.parseInt(input[y][x] + "") + 1);
            }
        }
        int sum = 0;
        for (int i = 0; i < mem.size(); i++) {
            sum += mem.get(i);
        }
        return sum;
    }

    private static boolean isThereLower(int y, int x) {
        if (y != 0) {
            if (input[y - 1][x] <= input[y][x])
                return true;
        }
        if (x != 0) {
            if (input[y][x - 1] <= input[y][x])
                return true;
        }
        if (y != input.length - 1) {
            if (input[y + 1][x] <= input[y][x])
                return true;
        }
        if (x != input[0].length - 1) {
            if (input[y][x + 1] <= input[y][x])
                return true;
        }
        return false;
    }

    public static int task2() {
        return 0;

    }

    public static int[][] convert(String[] input) {
        int[][] mem = new int[input.length][100];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < 100; j++) {
                mem[i][j] = Character.getNumericValue(input[i].charAt(j));
            }
        }
        return mem;
    }
}