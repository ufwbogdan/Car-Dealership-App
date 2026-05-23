package service;
import model.Customer;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private static CustomerService instance;
    private final List<Customer> customerList = new ArrayList<>();

    private CustomerService() {}

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    public void addCustomer(Customer c) {
        customerList.add(c);
    }

    public void removeCustomer(String email) {
        if (searchByEmail(email) == null) {
            throw new IllegalArgumentException("Customer with email " + email + " not found!");
        }
        customerList.removeIf(c -> c.getEmail().equalsIgnoreCase(email));
    }

    public List<Customer> getCustomers() {
        return customerList;
    }

    public Customer searchByEmail(String email) {
        return customerList.stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
}
