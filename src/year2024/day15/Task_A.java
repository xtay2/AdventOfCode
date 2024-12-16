package year2024.day15;

import aoc.AdventOfCode;
import aoc.Task;
import util.CharMatrix;
import util.Direction;
import util.IPoint;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Task_A extends Task {

    static {
        new Task_A();
    }

    @Override
    protected Object exec(AdventOfCode aoc) {
        var input = aoc.inputTxt().split("\\R\\R");
        var maze = new Maze(null, new HashSet<>(), new HashSet<>());
        new CharMatrix(Arrays.stream(input[0].split("\\R"))
                .map(String::toCharArray)
                .toArray(char[][]::new))
                .foreach((x, y, val) -> {
                    switch (val) {
                        case '@' -> maze.robot = new IPoint(x, y);
                        case '#' -> maze.walls.add(new IPoint(x, y));
                        case 'O' -> maze.boxes.add(new IPoint(x, y));
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

    class Maze {
        IPoint robot;
        Set<IPoint> boxes;
        Set<IPoint> walls;

        Maze(IPoint robot, Set<IPoint> boxes, Set<IPoint> walls) {
            this.robot = robot;
            this.boxes = boxes;
            this.walls = walls;
        }

        void move(Direction dir) {
            var robotNext = robot.neighbour(dir);
            if(!walls.contains(robotNext)) {
                if(!boxes.contains(robotNext))
                    robot = robotNext;
                else if (canMoveNextBoxes(dir)) {
                    var nextBoxes = nextBoxes(dir);
                    robot = robotNext;
                    boxes.removeAll(nextBoxes);
                    nextBoxes.forEach(box -> boxes.add(box.neighbour(dir)));
                }
            }
        }

        boolean canMoveNextBoxes(Direction dir) {
            return nextBoxes(dir).stream()
                    .map(box -> box.neighbour(dir))
                    .noneMatch(walls::contains);
        }

        Set<IPoint> nextBoxes(Direction dir) {
            var nextBoxes = new HashSet<IPoint>();
            var next = robot.neighbour(dir);
            for (; !walls.contains(next) && boxes.contains(next); next = next.neighbour(dir))
                nextBoxes.add(next);
            return nextBoxes;
        }

        int gpsSum() {
            return boxes.stream()
                    .mapToInt(box -> 100 * box.y() + box.x())
                    .sum();
        }
    }
}