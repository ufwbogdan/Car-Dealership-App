package service;

import model.Department;
import model.Employee;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeCsvService {
    private static EmployeeCsvService instance;
    private static final String FILE_PATH = "employees.csv";
    private final CsvService csvService = CsvService.getInstance();

    private EmployeeCsvService() {}

    public static EmployeeCsvService getInstance() {
        if (instance == null) {
            instance = new EmployeeCsvService();
        }
        return instance;
    }

    public void saveEmployees(List<Employee> employees) {
        List<String> lines = new ArrayList<>();
        for (Employee e : employees) {
            lines.add(e.getName() + "," + e.getEmail() + "," + e.getPhoneNumber() + "," + e.getDepartment().getName() + "," + e.getDepartment().getDescription() + "," +
                    e.getSalary() + "," + e.getHireDate());
        }
        csvService.writeToFile(FILE_PATH, lines);
    }

    public List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>();
        List<String> lines = csvService.readFromFile(FILE_PATH);
        for (String line : lines) {
            String[] parts = line.split(",");
            Department department = new Department(parts[3], parts[4]);
            Employee e = new Employee(parts[0], parts[1], parts[2], department, Integer.parseInt(parts[5]), LocalDate.parse(parts[6]));
            employees.add(e);
        }
        return employees;
    }
}