package aoc;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Year;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

/**
 * Represents the info of one specific day.
 */
public class AdventOfCode {

    /**
     * The path for the oauth token file.
     */
    private static final Path TOKEN_PATH = Path.of("files/token.txt");

    public final int year;
    public final int day;

    AdventOfCode(Class<? extends Task> task) {
        var name = task.getCanonicalName();

        int yearIdx = name.indexOf("year") + 4;
        this.year = parseInt(name.substring(yearIdx, yearIdx + 4));

        int dayIdx = name.indexOf("day") + 3;
        this.day = parseInt(name.substring(dayIdx, dayIdx + 2));
    }

    /**
     * Returns the input of the current task as a {@link List} of lines.
     */
    public List<String> inputLst() {
        updateInput();
        try {
            return Files.readAllLines(inputFile());
        } catch (IOException ioe) {
            throw new AssertionError("Could not read input.", ioe);
        }
    }

    /**
     * Returns the input of the current task as a {@link Stream} of lines.
     */
    public Stream<String> inputStr() {
        return inputLst().stream();
    }

    /**
     * Returns the input of the current task as one {@link String}.
     */
    public String inputTxt() {
        updateInput();
        try {
            return Files.readString(inputFile());
        } catch (IOException ioe) {
            throw new AssertionError("Could not read input.", ioe);
        }
    }

    /**
     * Returns the input of the current task as an array of lines.
     */
    public String[] inputArr() {
        return inputTxt().split("\n");
    }

    /**
     * Returns the input of the current task as a matrix of chars.
     */
    public char[][] inputMat() {
        return inputStr()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    /**
     * Returns the input of the current task as an array of {@link Character}s.
     */
    public Character[] inputChars() {
        return inputTxt().chars().mapToObj(c -> (char) c).toArray(Character[]::new);
    }

    // FILES

    private void updateInput() {
        if (year < 2015 || year > Year.now().getValue())
            throw new IllegalArgumentException("Please choose a valid year. (2015-" + Year.now().getValue() + ") Was: " + year);
        if (day < 1 || day > 24)
            throw new IllegalArgumentException("Please choose a valid day. (1-24) Was: " + day);
        try {
            if (Files.exists(inputFile()) && !Files.readString(inputFile()).equals("Puzzle inputs differ by user. Please log in to get your puzzle input.\n"))
                return;
            if (Files.notExists(TOKEN_PATH))
                throw new IllegalStateException("Please create a file with your auth token!");
            var request = HttpRequest
                    .newBuilder(URI.create("https://adventofcode.com/" + year + "/day/" + day + "/input"))
                    .GET()
                    .setHeader("Cookie", "session=" + Files.readString(TOKEN_PATH))
                    .build();
            Files.createDirectories(inputFile().getParent());
            var response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 404)
                return;
            Files.writeString(inputFile(), response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Path inputFile() {
        return Path.of("files/input/" + year + "/day_" + day + ".txt");
    }


}