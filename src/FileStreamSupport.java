
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class FileStreamSupport {
    public static final String DEFAULT_DELIMITER = "\n";
    public static final long DEFAULT_SIZE_ESTIMATE = 100;

    private FileStreamSupport() {}

    public static Stream<String> toStream(InputStream inputStream) {
        return toStream(inputStream, DEFAULT_DELIMITER, DEFAULT_SIZE_ESTIMATE);
    }
    public static Stream<String> toStream(InputStream inputStream, String delimiter) {
        return toStream(inputStream, delimiter, DEFAULT_SIZE_ESTIMATE);
    }
    public static Stream<String> toStream(InputStream inputStream, long sizeEstimate) {
        return toStream(inputStream, DEFAULT_DELIMITER, sizeEstimate);
    }
    public static Stream<String> toStream(InputStream inputStream, String delimiter, long sizeEstimate) {
        Scanner scanner = new Scanner(inputStream).useDelimiter(Pattern.compile(delimiter));
        Spliterator<String> spliterator =
                Spliterators.spliterator(scanner, sizeEstimate, Spliterator.NONNULL | Spliterator.IMMUTABLE);
        return StreamSupport.stream(spliterator, false)
                .onClose(scanner::close);
    }

}