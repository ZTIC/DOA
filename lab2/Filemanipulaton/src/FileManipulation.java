import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class FileManipulation {
    public static void main(String[] args) {
        writeTextToFile("student_info.txt", "Name: Maria Silva");
        readTextFromFile("student_info.txt");
    }

    // Creating a new file if file does not exist
    // If file already exists, merge it
    public static void writeTextToFile(String filename, String content) {
        Path path = Paths.get(filename);
        // If file does not exist then create new one
        if (!Files.exists(path)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
                writer.write(content);
                writer.newLine();
                writer.write("Food I Like: Sushi, Pasta, Ice Cream");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {// Append the text
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
                writer.newLine();
                writer.write(content);
                writer.newLine();
                writer.write("Food I Like: Sushi, Pasta, Ice Cream");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Reade a file given
    public static void readTextFromFile(String filename) {
        Path path = Paths.get(filename);

        if (!Files.exists(path)) {
            throw new RuntimeException(STR."File not found: \{filename}");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
