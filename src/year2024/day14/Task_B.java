package year2024.day14;

import aoc.AdventOfCode;
import aoc.Task;
import util.IPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Task_B extends Task {

    static {
        new Task_B();
    }

    @Override
    protected Object exec(AdventOfCode aoc) throws Exception {
        var pattern = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)");
        var maxSeconds = 10_000;
        var bounds = new IPoint(101, 103);
        var movedRobots = aoc.inputStr()
                .map(line -> {
                    var matcher = pattern.matcher(line);
                    matcher.find();
                    var pos = new IPoint(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                    var vel = new IPoint(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
                    return new Robot(pos, vel);
                }).toList();
        Files.createDirectories(Path.of("files/output/year2024/day14"));
        for (int seconds = 0; seconds < maxSeconds; seconds++) {
            var img = new BufferedImage(bounds.x(), bounds.y(), BufferedImage.TYPE_INT_RGB);
            for (var movedRobot : movedRobots) {
                var p = movedRobot.pos.plus(movedRobot.vel.times(seconds)).map(x -> Math.floorMod(x, bounds.x()), y -> Math.floorMod(y, bounds.y()));
                img.setRGB(p.x(), p.y(), Color.WHITE.getRGB());
            }
            ImageIO.write(img, "png", Path.of("files/output/year2024/day14/Task_B_" + seconds + ".png").toFile());
        }
        return null;
    }

    record Robot(IPoint pos, IPoint vel) {}
}