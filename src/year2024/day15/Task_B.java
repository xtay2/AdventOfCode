package year2024.day15;

import aoc.AdventOfCode;
import aoc.Task;
import util.CharMatrix;
import util.Direction;
import util.IPoint;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var input = aoc.inputTxt().split("\\R\\R");
        var maze = new Maze(null, new HashSet<>(), new HashSet<>());
        new CharMatrix(Arrays.stream(input[0]
                        .replaceAll("#", "##")
                        .replaceAll("O", "[]")
                        .replaceAll("\\.", "..")
                        .replaceAll("@", "@.")
                        .split("\\R"))
                .map(String::toCharArray)
                .toArray(char[][]::new))
                .foreach((x, y, val) -> {
                    switch (val) {
                        case '@' -> maze.robot = new IPoint(x, y);
                        case '#' -> maze.walls.add(new IPoint(x, y));
                        case '[' -> maze.boxes.add(new DoubleBox(new IPoint(x, y), new IPoint(x + 1, y)));
                    }
                });
        input[1].replaceAll("\\R", "").chars().forEachOrdered(c -> {
            switch (c) {
                case '^' -> maze.move(Direction.UP);
                case 'v' -> maze.move(Direction.DOWN);
                case '<' -> maze.move(Direction.LEFT);
                case '>' -> maze.move(Direction.RIGHT);
                default -> throw new IllegalArgumentException("Invalid character: " + (char) c);
            }
        });

        return maze.gpsSum();
    }

    record DoubleBox(IPoint box1, IPoint box2) {

        DoubleBox neighbour(Direction dir) {
            return new DoubleBox(box1.neighbour(dir), box2.neighbour(dir));
        }

    }

    class Maze {
        IPoint robot;
        Set<DoubleBox> boxes;
        Set<IPoint> walls;

        Maze(IPoint robot, Set<DoubleBox> boxes, Set<IPoint> walls) {
            this.robot = robot;
            this.boxes = boxes;
            this.walls = walls;
        }

        void move(Direction dir) {
            var robotNext = robot.neighbour(dir);
            if (!walls.contains(robotNext)) {
                if (boxAt(robotNext).isEmpty())
                    robot = robotNext;
                else if (canMoveNextBoxes(dir)) {
                    var nextBoxes = nextBoxes(robot, dir);
                    robot = robotNext;
                    boxes.removeAll(nextBoxes);
                    nextBoxes.forEach(box -> boxes.add(box.neighbour(dir)));
                }
            }
        }

        boolean canMoveNextBoxes(Direction dir) {
            return nextBoxes(robot, dir).stream()
                    .map(box -> box.neighbour(dir))
                    .flatMap(box -> Stream.of(box.box1, box.box2))
                    .noneMatch(walls::contains);
        }

        Set<DoubleBox> nextBoxes(IPoint point, Direction dir) {
            var optNextBox = boxAt(point.neighbour(dir));
            if (optNextBox.isEmpty())
                return Set.of();
            var nextBox = optNextBox.get();
            Stream.Builder<DoubleBox> b = Stream.builder();
            b.add(nextBox);
            if (dir != Direction.RIGHT)
                nextBoxes(nextBox.box1, dir).forEach(b::add);
            if (dir != Direction.LEFT)
                nextBoxes(nextBox.box2, dir).forEach(b::add);
            return b.build().collect(Collectors.toUnmodifiableSet());
        }

        Optional<DoubleBox> boxAt(IPoint point) {
            return boxes.stream()
                    .filter(box -> box.box1.equals(point) || box.box2.equals(point))
                    .findAny();
        }

        int gpsSum() {
            return boxes.stream()
                    .mapToInt(box -> 100 * box.box1.y() + box.box1.x())
                    .sum();
        }
    }
}