package service;
import model.Department;
import model.Employee;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class EmployerService {
    private static EmployerService instance;
    private final TreeSet<Employee> employeeList = new TreeSet<>();

    private EmployerService() {}

    public static EmployerService getInstance() {
        if (instance == null) {
            instance = new EmployerService();
        }
        return instance;
    }

    public Employee searchByEmail(String email) {
        return employeeList.stream()
                .filter(e -> e.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public void addEmployee(Employee e) {
        employeeList.add(e);
    }

    public void removeEmployee(String email) {
        if (searchByEmail(email) == null) {
            throw new IllegalArgumentException("Employee with email " + email + " not found!");
        }
        employeeList.removeIf(e -> e.getEmail().equalsIgnoreCase(email));
    }

    public List<Employee> getEmployees() {
        return new ArrayList<>(employeeList);
    }

    public List<Employee> getByDepartment(String department) {
        return employeeList.stream()
                .filter(e -> e.getDepartment().getName().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    public List<Employee> sortByHireDate() {
        return employeeList.stream()
                .sorted(Comparator.comparing(Employee::getHireDate))
                .collect(Collectors.toList());
    }

    public void updateEmployee(Employee e, String newName, String newEmail, String newPhoneNumber, Department newDepartment, int newSalary, LocalDate newHireDate) {
        employeeList.remove(e);
        e.setName(newName);
        e.setEmail(newEmail);
        e.setPhoneNumber(newPhoneNumber);
        e.setDepartment(newDepartment);
        e.setSalary(newSalary);
        e.setHireDate(newHireDate);
        employeeList.add(e);
    }
}
