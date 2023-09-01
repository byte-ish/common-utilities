package writers;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceCSVWriter {

    private static final Object lock = new Object();
    public static void main(String[] args) {
        // Define the path to your CSV file
        String csvFilePath = "data.csv";

        // Create an ExecutorService with two threads
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Submit tasks to the ExecutorService
        executorService.submit(() -> writeToCSV(csvFilePath, "John", "Doe", "john@example.com"));
        executorService.submit(() -> writeToCSV(csvFilePath, "Jane", "Smith", "jane@example.com"));

        // Shutdown the ExecutorService when done
        executorService.shutdown();

        System.out.println("Data writing tasks submitted.");
    }

    private static void writeToCSV(String csvFilePath, String firstName, String lastName, String email) {
        synchronized (lock) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath, true))) {
                // Create a string array for the data
                String[] data = {firstName, lastName, email};

                // Write the data to the CSV file
                writer.writeNext(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
