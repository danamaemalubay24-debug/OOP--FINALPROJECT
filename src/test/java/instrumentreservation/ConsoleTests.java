package instrumentreservation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsoleTests {
    private static final String INPUT_EXTENSION = ".input";
    private static final String EXPECTED_EXTENSION = ".expected";
    private static final Set<String> MENU_LINES = new HashSet<>(Arrays.asList(
            "INSTRUMENT RESERVATION SYSTEM",
            "1. Add Instrument",
            "2. View Instruments",
            "3. Search Instrument",
            "4. Make Reservation",
            "5. View Reservations",
            "6. My Borrowed Items",
            "7. Return Instrument",
            "8. Update Instrument Info",
            "9. Delete Instrument",
            "10. Exit"
    ));
    private static final Pattern PROMPT_PREFIX = Pattern.compile(
            "^(?:(?:Enter your choice|Instrument name|Instrument type|Enter an instrument ID, name, or type"
                    + "|Instrument ID|Borrower's name|Enter your name|New (?:name|type) \\(Enter to keep .*?\\)): )+"
    );

    public static void main(String[] args) throws IOException {
        Path casesDirectory = Paths.get(args[0]);
        List<Path> fixtures;
        try (Stream<Path> paths = Files.walk(casesDirectory)) {
            fixtures = paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(INPUT_EXTENSION)
                            || path.toString().endsWith(EXPECTED_EXTENSION))
                    .sorted()
                    .collect(Collectors.toList());
        }
        if (fixtures.isEmpty()) {
            throw new IllegalArgumentException("No console fixtures found in " + casesDirectory);
        }

        int passed = 0;
        int failed = 0;
        for (Path fixture : fixtures) {
            String fileName = fixture.getFileName().toString();
            boolean isInput = fileName.endsWith(INPUT_EXTENSION);
            int extensionIndex = fileName.lastIndexOf('.');
            Path pair = fixture.resolveSibling(fileName.substring(0, extensionIndex)
                    + (isInput ? EXPECTED_EXTENSION : INPUT_EXTENSION));
            String casePath = casesDirectory.relativize(fixture).toString();
            if (!Files.isRegularFile(pair)) {
                System.err.printf("FAIL %s is missing its matching %s file.%n", casePath, pair.getFileName());
                failed++;
                continue;
            }
            if (!isInput) {
                continue;
            }

            try {
                assertOutput(fixture, pair);
                passed++;
            } catch (IOException | RuntimeException | AssertionError failure) {
                System.err.printf("FAIL %s%n%s%n", casePath, failure);
                failed++;
            }
        }

        System.out.printf("Console regression tests: %d passed, %d failed.%n", passed, failed);
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void assertOutput(Path input, Path expectedFile) throws IOException {
        List<String> expected = Files.readAllLines(expectedFile, StandardCharsets.UTF_8);
        List<String> actual = Arrays.stream(runApp(input).split("\\R"))
                .map(line -> PROMPT_PREFIX.matcher(line).replaceFirst(""))
                .filter(line -> !line.isEmpty() && !MENU_LINES.contains(line))
                .collect(Collectors.toList());
        if (!expected.equals(actual)) {
            throw new AssertionError(String.format("Expected output%n%s%nActual output%n%s",
                    String.join(System.lineSeparator(), expected), String.join(System.lineSeparator(), actual)));
        }
    }

    private static String runApp(Path input) throws IOException {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        byte[] commands = Files.readAllBytes(input);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream captured = new PrintStream(output, true, StandardCharsets.UTF_8.name())) {
            System.setIn(new ByteArrayInputStream(commands));
            System.setOut(captured);
            Main.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return new String(output.toByteArray(), StandardCharsets.UTF_8);
    }
}
