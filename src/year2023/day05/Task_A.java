package year2023.day05;

import aoc.AdventOfCode;
import aoc.Task;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class Task_A extends Task {

    static {
        new Task_A();
    }


    @Override
    protected Object exec(AdventOfCode aoc) {
        var mappingGroups = Stream.of(aoc.inputTxt())
                .map(input -> input.split("\n\n"))
                .flatMap(Arrays::stream)
                .skip(1) // Überspringe erste Zeile
                .map(group -> group.split("\n"))
                .map(Arrays::stream)
                .map(group -> group.skip(1)) // Überspringe erste Zeile jeder Gruppe
                .map(group -> group.map(line -> line.split(" "))
                        .map(Arrays::stream)
                        .map(line -> line.mapToLong(Long::parseLong).toArray())
                        .map(line -> new SourceToDestMapping(line[0], line[1], line[2])))
                .map(group -> group.toArray(SourceToDestMapping[]::new))
                .toArray(SourceToDestMapping[][]::new);
        return aoc.inputStr()
                .limit(1)
                .map(line -> line.substring("seeds: ".length()))
                .map(line -> line.split(" "))
                .flatMap(Arrays::stream)
                .mapToLong(Long::parseLong)
                .map(seed -> {
                    var value = new AtomicLong(seed);
                    Arrays.stream(mappingGroups)
                            .map(Arrays::stream)
                            .forEach(group -> group.filter(mapping -> value.get() >= mapping.sourceRangeStart() && value.get() < mapping.sourceRangeStart() + mapping.rangeLength())
                                    .findFirst()
                                    .ifPresent(mapping -> value.set(mapping.destRangeStart() + (value.get() - mapping.sourceRangeStart())))
                            );
                    return value.get();
                })
                .min()
                .orElseThrow();
    }


    record SourceToDestMapping(long destRangeStart, long sourceRangeStart, long rangeLength) {}

}