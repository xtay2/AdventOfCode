package helper;

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

public class AdventOfCode {

	public final int year;
	public final int day;

	AdventOfCode(Class<? extends Task> task) {
		var name = task.getCanonicalName();

		int yearIdx = name.indexOf("year") + 4;
		this.year = Integer.parseInt(name.substring(yearIdx, yearIdx + 4));

		int dayIdx = name.indexOf("day") + 2;
		this.day = Integer.parseInt(name.substring(dayIdx, dayIdx + 2));
	}

	private static final Path TOKEN_PATH = Path.of("token.txt");

	public List<String> inputLst() {
		updateInput();
		try {
			return Files.readAllLines(inputFile());
		} catch (IOException ioe) {
			throw new AssertionError("Could not read input.", ioe);
		}
	}

	public Stream<String> inputStr() {
		return inputLst().stream();
	}

	public String inputTxt() {
		updateInput();
		try {
			return Files.readString(inputFile());
		} catch (IOException ioe) {
			throw new AssertionError("Could not read input.", ioe);
		}
	}

	public String[] inputArr() {
		return inputTxt().split("\n");
	}

	// FILES

	private void updateInput() {
		if (year < 2015 || year > Year.now().getValue())
			throw new IllegalArgumentException("Please choose a valid year. (2015-" + Year.now().getValue() + ") Was: " + year);
		if (day < 1 || day > 24)
			throw new IllegalArgumentException("Please choose a valid day. (1-24) Was: " + day);
		if (Files.exists(inputFile()))
			return;
		try {
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
		return Path.of("input/" + year + "/day_" + day + ".txt");
	}


}
