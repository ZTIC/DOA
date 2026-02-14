package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {
    private CSVUtil() {
    }

    // Read

    /**
     *
     */
    public static List<String> readCSV(String filename) {
        Path path = Paths.get(filename);
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
                return new ArrayList<>();
            }
            List<String> lines = Files.readAllLines(path);
            return lines.stream()
                    .filter(line -> !line.isBlank())
                    .toList();
        } catch (IOException exception) {
            throw new RuntimeException(
                    "Error reading CSVFile: " + filename, exception
            );
        }
    }

    // Write
    public static void writeCSV(String filename, List<String> lines) {
        Path path = Paths.get(filename);
        try {
            Files.write(path, lines);
        } catch (IOException e) {
            throw new RuntimeException("Error writing csv file: " + filename, e);
        }
    }

}
