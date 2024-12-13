package year2024.day06;

import aoc.AdventOfCode;
import aoc.Task;
import util.CharMatrix;
import util.Direction;
import util.Point;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    Guard guard;
    CharMatrix matrix;
    int w, h;

    @Override
    protected Object exec(AdventOfCode aoc) {
        matrix = aoc.inputCharMat();
        w = matrix.width();
        h = matrix.height();
        var cnt = 0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (matrix.get(x, y) == '.') {
                    matrix.set(x, y, '#');
                    guard = findGuard();
                    guard.goWhileNotInLoop();
                    if (guard.isInLoop()) {cnt++;}
                    matrix = aoc.inputCharMat();
                }
            }
        }
        return cnt;
    }

    private Guard findGuard() {
        return matrix.map((x, y, v) -> {
            var rot = switch (v) {
                case '^' -> Direction.UP;
                case 'v' -> Direction.DOWN;
                case '<' -> Direction.LEFT;
                case '>' -> Direction.RIGHT;
                default -> null;
            };
            if (rot != null)
                return new Guard(new Point(x, y), rot);
            return null;
        }).filter(Objects::nonNull).findFirst().orElseThrow();
    }

    class Guard {

        Set<GuardPos> visited = new HashSet<>();
        Point pos;
        Direction direction;

        Guard(Point pos, Direction direction) {
            this.pos = pos;
            this.direction = direction;
            matrix.set(pos, '.');
        }

        void goWhileNotInLoop() {
            check:
            while (!guard.isOutOfMap()) {
                while (!guard.obstacleAhead()) {
                    if (guard.isOutOfMap() || guard.isInLoop())
                        break check;
                    guard.go();
                }
                guard.turn();
            }
        }

        void go() {
            visited.add(pos());
            pos = pos.neighbour(direction);
        }

        void turn() {
            direction = switch (direction) {
                case UP -> Direction.RIGHT;
                case DOWN -> Direction.LEFT;
                case LEFT -> Direction.UP;
                case RIGHT -> Direction.DOWN;
            };
        }

        boolean obstacleAhead() {
            var nextPos = pos.neighbour(direction);
            return matrix.isInBounds(nextPos) && matrix.get(nextPos) == '#';
        }

        boolean isOutOfMap() {
            return !matrix.isInBounds(pos);
        }

        boolean isInLoop() {
            return visited.contains(pos());
        }

        GuardPos pos() {
            return new GuardPos(pos, direction);
        }

        record GuardPos(Point pos, Direction rotation) {}
    }

}