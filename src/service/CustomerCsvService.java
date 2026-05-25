package service;

import model.Customer;
import java.util.ArrayList;
import java.util.List;

public class CustomerCsvService {
    private static CustomerCsvService instance;
    private static final String FILE_PATH = "customers.csv";
    private final CsvService csvService = CsvService.getInstance();

    private CustomerCsvService() {}

    public static CustomerCsvService getInstance() {
        if (instance == null) {
            instance = new CustomerCsvService();
        }
        return instance;
    }

    public void saveCustomers(List<Customer> customers) {
        List<String> lines = new ArrayList<>();
        for (Customer c : customers) {
            lines.add(c.getName() + "," + c.getEmail() + "," + c.getPhoneNumber() + "," + c.getCreditScore());
        }
        csvService.writeToFile(FILE_PATH, lines);
    }

    public List<Customer> loadCustomers() {
        List<Customer> customers = new ArrayList<>();
        List<String> lines = csvService.readFromFile(FILE_PATH);
        for (String line : lines) {
            String[] parts = line.split(",");
            Customer c = new Customer(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            customers.add(c);
        }
        return customers;
    }
}