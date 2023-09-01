package writers;

import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class OptimizedConcurrentCSVWriter {
    public static void main(String[] args) {
        // Define the path to your CSV file
        Path csvFilePath = Path.of("data.csv");

        // Create a string array for the data
        String[] data = {"John", "Doe", "john@example.com"};

        // Attempt to write data to the CSV file
        if (writeToCSV(csvFilePath, data)) {
            System.out.println("Data written to " + csvFilePath);
        } else {
            System.out.println("Failed to write data to " + csvFilePath);
        }
    }

    private static boolean writeToCSV(Path csvFilePath, String[] data) {
        try (FileChannel channel = FileChannel.open(csvFilePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
             FileLock lock = channel.lock()) {

            // Append a new line with the data to the CSV file
            try (BufferedWriter writer = java.nio.file.Files.newBufferedWriter(csvFilePath, StandardOpenOption.APPEND)) {
                List<String> rowData = Arrays.asList(data);
                String row = String.join(",", rowData);
                writer.write(row);
                writer.newLine();
            }

            return true;
        } catch (FileAlreadyExistsException e) {
            // Handle the case where the file already exists
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
