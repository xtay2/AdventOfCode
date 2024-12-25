package year2024.day06;

import aoc.AdventOfCode;
import aoc.Task;
import util.CharMatrix;
import util.Direction;
import util.IPoint;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    Guard guard;
    CharMatrix matrix;

    @Override
    protected Object exec(AdventOfCode aoc) {
        matrix = aoc.inputCharMat();
        guard = matrix.stream()
                .map(cvc -> {
            var rot = Direction.fromChar(cvc.val());
            if (rot != null)
                return new Guard(cvc.pos(), rot);
            return null;
        }).filter(Objects::nonNull).findFirst().orElseThrow();
        guard.goWhileInMap();
        return guard.visited.size();
    }

    class Guard {

        Set<IPoint> visited = new HashSet<>();
        IPoint pos;
        Direction direction;

        Guard(IPoint pos, Direction direction) {
            this.pos = pos;
            this.direction = direction;
            matrix.set(pos, '.');
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
            visited.add(pos);
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

    }

}