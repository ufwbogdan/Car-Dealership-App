package service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvService {
    private static CsvService instance;

    private CsvService() {}

    public static CsvService getInstance() {
        if (instance == null) {
            instance = new CsvService();
        }
        return instance;
    }

    public void writeToFile(String filePath, List<String> lines) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public List<String> readFromFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return lines;
    }
}