package service;

import model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionCsvService {
    private static TransactionCsvService instance;
    private static final String FILE_PATH = "transactions.csv";
    private final CsvService csvService = CsvService.getInstance();

    private TransactionCsvService() {}

    public static TransactionCsvService getInstance() {
        if (instance == null) {
            instance = new TransactionCsvService();
        }
        return instance;
    }

    public void saveTransactions(List<Transaction> transactions) {
        List<String> lines = new ArrayList<>();
        for (Transaction t : transactions) {
            lines.add(t.getId() + "," +
                    t.getVehicle().getVehicleBrand() + "," +
                    t.getVehicle().getVehicleModel() + "," +
                    t.getVehicle().getPrice() + "," +
                    t.getCustomer().getEmail() + "," +
                    t.getDate());
        }
        csvService.writeToFile(FILE_PATH, lines);
    }

    public List<Transaction> loadTransactions(List<Customer> customers) {
        List<Transaction> transactions = new ArrayList<>();
        List<String> lines = csvService.readFromFile(FILE_PATH);
        for (String line : lines) {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            String brand = parts[1];
            String model = parts[2];
            int price = Integer.parseInt(parts[3]);
            String customerEmail = parts[4];
            LocalDateTime date = LocalDateTime.parse(parts[5]);

            Vehicle vehicle = new CombustionVehicle(0, price, 0, 0, "", brand, model, "", 0);

            Customer customer = customers.stream()
                    .filter(c -> c.getEmail().equalsIgnoreCase(customerEmail))
                    .findFirst()
                    .orElse(null);

            if (customer != null) {
                transactions.add(new Transaction(id, vehicle, customer, date));
            }
        }
        return transactions;
    }
}