package year2024.day06;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.HashSet;
import java.util.Set;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    Guard guard;
    char[][] matrix;
    int w, h;

    @Override
    protected Object exec(AdventOfCode aoc) {
        matrix = aoc.inputMat();
        w = matrix[0].length;
        h = matrix.length;
        guard = findGuard();
        guard.goWhileInMap();
        return guard.visited.size() + 1;
    }

    private Guard findGuard() {
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                var rot = switch (matrix[y][x]) {
                    case '^' -> Rotation.UP;
                    case 'v' -> Rotation.DOWN;
                    case '<' -> Rotation.LEFT;
                    case '>' -> Rotation.RIGHT;
                    default -> null;
                };
                if (rot != null)
                    return new Guard(x, y, rot);
            }
        }
        throw new AssertionError("No guard found");
    }

    enum Rotation {UP, DOWN, LEFT, RIGHT}

    class Guard {

        Set<GuardPos> visited = new HashSet<>();
        int x, y;
        Rotation rotation;

        Guard(int x, int y, Rotation rotation) {
            this.x = x;
            this.y = y;
            this.rotation = rotation;
            matrix[y][x] = '.';
        }

        void goWhileInMap() {
            check:
            while (!guard.isOutOfMap()) {
                while (!guard.obstacleAhead()) {
                    if (guard.isOutOfMap())
                        break check;
                    guard.go();
                }
                guard.turn();
            }
        }

        void go() {
            visited.add(pos());
            switch (rotation) {
                case UP -> y--;
                case DOWN -> y++;
                case LEFT -> x--;
                case RIGHT -> x++;
            }
        }

        void turn() {
            rotation = switch (rotation) {
                case UP -> Rotation.RIGHT;
                case DOWN -> Rotation.LEFT;
                case LEFT -> Rotation.UP;
                case RIGHT -> Rotation.DOWN;
            };
        }

        boolean obstacleAhead() {
            return switch (rotation) {
                case UP -> y - 1 >= 0 && matrix[y - 1][x] == '#';
                case DOWN -> y + 1 < h && matrix[y + 1][x] == '#';
                case LEFT -> x - 1 >= 0 && matrix[y][x - 1] == '#';
                case RIGHT -> x + 1 < w && matrix[y][x + 1] == '#';
            };
        }

        boolean isOutOfMap() {
            return x <= 0 || x >= w || y <= 0 || y >= h;
        }

        GuardPos pos() {
            return new GuardPos(x, y);
        }

        @Override
        public String toString() {
            return "Guard at [" + x + ", " + y + " | " + rotation + "]";
        }

        record GuardPos(int x, int y) {}
    }

}