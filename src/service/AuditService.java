package service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static AuditService instance;
    private static final String FILE_PATH = "audit.csv";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private AuditService() {}

    public static AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public void log(String actionName) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true)) {
            fw.write(actionName + "," + LocalDateTime.now().format(formatter) + "\n");
        } catch (IOException e) {
            System.out.println("Audit error: " + e.getMessage());
        }
    }
}