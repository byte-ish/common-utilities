package writers;

import com.opencsv.CSVWriter;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class ConcurrentCSVWriter {
    public static void main(String[] args) {
        // Define the path to your CSV file
        String csvFilePath = "data.csv";

        // Try to obtain an exclusive lock on the CSV file
        try (FileChannel channel = new FileOutputStream(csvFilePath).getChannel(); FileLock lock = channel.lock()) {

            // Create a string array for the data
            String[] data = {"John", "Doe", "john@example.com"};

            // Write the data to the CSV file
            try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath, true))) {
                writer.writeNext(data);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Data written to " + csvFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}