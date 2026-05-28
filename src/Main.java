import model.*;
import service.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Please enter a number:");
            scanner.nextLine();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private static double readDouble(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input! Please enter a number:");
            scanner.nextLine();
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }

    private static String readString(Scanner scanner) {
        String value = scanner.nextLine();
        while (value.trim().isEmpty()) {
            System.out.println("Invalid input! Please enter a valid text:");
            value = scanner.nextLine();
        }
        return value;
    }

    public static void main(String[] args) {
        VehicleService vehicleService = VehicleService.getInstance();
        CustomerService customerService = CustomerService.getInstance();
        EmployerService employerService = EmployerService.getInstance();
        TransactionService transactionService = TransactionService.getInstance();

        try {
            VehicleJdbcService.getInstance().read().forEach(vehicleService::addVehicle);

            vehicleService.setNextId(
                    vehicleService.getVehicles().stream()
                            .mapToInt(Vehicle::getId)
                            .max()
                            .orElse(0) + 1
            );

            EmployeeJdbcService.getInstance().read().forEach(employerService::addEmployee);
            CustomerJdbcService.getInstance().read().forEach(customerService::addCustomer);
            TransactionJdbcService.getInstance().read().forEach(transactionService::addTransaction);
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while(running) {
            System.out.println("===DEALERSHIP MANAGEMENT SYSTEM===");
            System.out.println("CHOOSE YOUR COMMAND:");
            System.out.println("1. Vehicle Management");
            System.out.println("2. Employer Management");
            System.out.println("3. Customer Management");
            System.out.println("0. Exit");

            int choice = readInt(scanner);

            switch (choice) {
                case 1 -> vehicleMenu(scanner, vehicleService, customerService);
                case 2 -> employeeMenu(scanner, employerService);
                case 3 -> customerMenu(scanner, customerService);
                case 0 -> running = false;
                default -> System.out.println("Invalid Option!");
            }

        }
        scanner.close();
    }

    private static void vehicleMenu(Scanner scanner, VehicleService vehicleService, CustomerService customerService) {
        System.out.println("===Welcome to the Vehicle Menu!===");
        boolean running = true;
        while (running) {
            System.out.println("Choose your option:");
            System.out.println("1. Add a Vehicle");
            System.out.println("2. Remove a Vehicle");
            System.out.println("3. Get all Vehicles");
            System.out.println("4. Filter Vehicles");
            System.out.println("5. Search Vehicles");
            System.out.println("6. Sort Vehicles");
            System.out.println("7. Update Vehicle");
            System.out.println("8. Compute financing");
            System.out.println("9. Sell Vehicle");
            System.out.println("10. Get Transactions");
            System.out.println("0. Back to Main Menu");

            int choice = readInt(scanner);

            switch (choice) {
                case 1 -> addVehicleMenu(scanner, vehicleService);
                case 2 -> removeVehicleMenu(scanner, vehicleService);
                case 3 -> vehicleService.getVehicles().forEach(System.out::println);
                case 4 -> filterVehicleMenu(scanner, vehicleService);
                case 5 -> searchVehicleMenu(scanner, vehicleService);
                case 6 -> vehicleService.sortByMileage().forEach(System.out::println);
                case 7 -> updateVehicleMenu(scanner, vehicleService);
                case 8 -> computeFinancingMenu(scanner, vehicleService);
                case 9 -> sellVehicleMenu(scanner, vehicleService, customerService);
                case 10 -> TransactionService.getInstance().getTransactionList().forEach(System.out::println);
                case 0 -> running = false;
                default -> System.out.println("Invalid Option!");
            }

        }

    }

    private static void addVehicleMenu(Scanner scanner, VehicleService vehicleService) {
        System.out.println("Choose the vehicle type:");
        System.out.println("1. Combustion Vehicle");
        System.out.println("2. Electric Vehicle");
        System.out.println("3. Hybrid Vehicle");

        int type = readInt(scanner);
        if  (type < 1 || type > 3) {
            System.out.println("Invalid option!");
            return;
        }

        System.out.println("Brand:");
        String brand = readString(scanner);

        System.out.println("Model:");
        String model = readString(scanner);

        System.out.println("Price:");
        int price = readInt(scanner);

        System.out.println("Mileage:");
        int mileage = readInt(scanner);

        System.out.println("Production Year:");
        int year = readInt(scanner);

        System.out.println("Vehicle Body:");
        String body = readString(scanner);

        switch (type) {
            case 1 -> {
                System.out.println("Fuel Type:");
                String fuelType = readString(scanner);
                System.out.println("Engine Capacity:");
                int cc = readInt(scanner);
                System.out.println("Fuel Consumption:");
                double fuelConsumption = readDouble(scanner);

                CombustionVehicle cv = new CombustionVehicle(mileage, price, year, cc, body, brand, model, fuelType, fuelConsumption);
                vehicleService.addVehicle(cv);
                try { VehicleJdbcService.getInstance().create(cv); } catch (SQLException e) { System.out.println(e.getMessage()); }
            }

            case 2 -> {
                System.out.println("Energy Consumption:");
                double energyConsumption = readDouble(scanner);

                ElectricVehicle ev = new ElectricVehicle(mileage, price, year, body, brand, model, energyConsumption);
                vehicleService.addVehicle(ev);
                try { VehicleJdbcService.getInstance().create(ev); } catch (SQLException e) { System.out.println(e.getMessage()); }
            }

            case 3 -> {
                System.out.println("Fuel Type:");
                String fuelType = readString(scanner);
                System.out.println("Engine Capacity:");
                int cc = readInt(scanner);
                System.out.println("Fuel Consumption (L/100km):");
                double fuelConsumption = readDouble(scanner);
                System.out.println("Energy Consumption (kWh/100km):");
                double energyConsumption = readDouble(scanner);

                CombustionVehicle combustionPart = new CombustionVehicle(mileage, price, year, cc, body, brand, model, fuelType, fuelConsumption);
                ElectricVehicle electricPart = new ElectricVehicle(mileage, price, year, body, brand, model, energyConsumption);
                HybridVehicle hv = new HybridVehicle(combustionPart, electricPart);
                vehicleService.addVehicle(hv);
                try { VehicleJdbcService.getInstance().create(hv); } catch (SQLException e) { System.out.println(e.getMessage()); }
            }

            default -> System.out.println("Invalid Option!");
        }
        System.out.println("Vehicle added successfully!");
        AuditService.getInstance().log("add_vehicle");
    }

    private static void removeVehicleMenu(Scanner scanner, VehicleService vehicleService) {
        System.out.println("Enter the vehicle's id that you want to remove:");
        int id = readInt(scanner);
        try {
            VehicleJdbcService.getInstance().delete(id);
            vehicleService.removeVehicle(id);
            System.out.println("Vehicle removed successfully!");
            AuditService.getInstance().log("remove_vehicle");
        } catch (SQLException e) {
            System.out.println("Error when trying to delete vehicle: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void filterVehicleMenu(Scanner scanner, VehicleService vehicleService) {
        System.out.println("Choose what to filter by:");
        System.out.println("1. Max mileage");
        System.out.println("2. Car Body");
        System.out.println("3. Production Year");

        int choice = readInt(scanner);
        switch (choice) {
            case 1 -> {
                System.out.println("Enter the max Mileage:");
                int maxMileage = readInt(scanner);
                vehicleService.filterByMaxMileage(maxMileage).forEach(System.out::println);
            }
            case 2 -> {
                System.out.println("Enter the car body:");
                String body = readString(scanner);
                vehicleService.filterByCarBody(body).forEach(System.out::println);
            }
            case 3 -> {
                System.out.println("Enter the min production year:");
                int minYear = readInt(scanner);
                System.out.println("Enter the max production year:");
                int maxYear = readInt(scanner);
                vehicleService.filterByYear(minYear, maxYear).forEach(System.out::println);
            }
            default -> System.out.println("Invalid Option!");
        }
        AuditService.getInstance().log("filter_vehicle");
    }

    private static void searchVehicleMenu(Scanner scanner, VehicleService vehicleService) {
        System.out.println("Enter the criteria you want to search by:");
        System.out.println("1. Search by Vehicle ID");
        System.out.println("2. Search by Production Year");
        System.out.println("3. Search by Brand");
        System.out.println("4. Search by Brand & Model");

        int choice = readInt(scanner);
        switch (choice) {
            case 1 -> {
                System.out.println("Enter the Vehicle's ID:");
                int id = readInt(scanner);
                Vehicle v = vehicleService.searchById(id);
                if (v == null) {
                    System.out.println("Vehicle with the given ID not found!");
                }
                else System.out.println(v);
            }
            case 2 -> {
                System.out.println("Enter the Production Year:");
                int prodYear = readInt(scanner);
                vehicleService.searchByYear(prodYear).forEach(System.out::println);
            }
            case 3 -> {
                System.out.println("Enter the Vehicle's Brand:");
                String brand = readString(scanner);
                vehicleService.searchByBrand(brand).forEach(System.out::println);
            }
            case 4 -> {
                System.out.println("Enter the Vehicle's Brand:");
                String brand = readString(scanner);
                System.out.println("Enter the Vehicle's Model:");
                String model = readString(scanner);
                vehicleService.searchByBrandAndModel(brand, model).forEach(System.out::println);
            }
            default -> System.out.println("Invalid Option!");
        }
        AuditService.getInstance().log("search_vehicles");
    }

    private static void updateVehicleMenu(Scanner scanner, VehicleService vehicleService) {
        System.out.println("Enter the vehicle's ID to update:");
        int id = readInt(scanner);
        Vehicle v = vehicleService.searchById(id);
        if (v == null) {
            System.out.println("Vehicle not found!");
            return;
        }
        System.out.println("Enter new brand:");
        String brand = readString(scanner);
        System.out.println("Enter new model:");
        String model = readString(scanner);
        System.out.println("Enter new price:");
        int price = readInt(scanner);
        System.out.println("Enter new mileage:");
        int mileage = readInt(scanner);
        System.out.println("Enter new vehicle body:");
        String body = readString(scanner);
        vehicleService.updateVehicle(v, mileage, price, body, brand, model);
        System.out.println("Vehicle updated successfully!");
        AuditService.getInstance().log("update_vehicle");
        try {
            VehicleJdbcService.getInstance().update(v);
        } catch (SQLException e) {
            System.out.println("Error when trying to update vehicle: " + e);
        }
    }

    private static void computeFinancingMenu(Scanner scanner, VehicleService vehicleService) {
        System.out.println("Enter the Vehicle's ID:");
        int id = readInt(scanner);
        Vehicle v = vehicleService.searchById(id);
        if (v == null) {
            System.out.println("Vehicle with the given ID cannot be found!");
            return;
        }
        System.out.println("Enter the amount of financing months:");
        int months = readInt(scanner);
        System.out.println("Enter the interest rate:");
        double interestRate = readDouble(scanner);
        double monthlyPayment = vehicleService.computeFinancing(v, months, interestRate);
        System.out.println("The monthly payment for this vehicle is " + monthlyPayment + " USD");
        AuditService.getInstance().log("compute_financing");
    }

    private static void sellVehicleMenu(Scanner scanner, VehicleService vehicleService, CustomerService customerService) {
        System.out.println("Enter the Vehicle's ID:");
        int id = readInt(scanner);
        System.out.println("Enter the customer's email:");
        String email = readString(scanner);
        Customer c = customerService.searchByEmail(email);
        if (c == null) {
            System.out.println("Customer with the given email cannot be found!");
            return;
        }
        try {
            vehicleService.sellVehicle(id, c);
            AuditService.getInstance().log("sell_vehicle");
            try {
                VehicleJdbcService.getInstance().delete(id);
                TransactionJdbcService.getInstance().create(TransactionService.getInstance().getTransactionList().getLast());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void employeeMenu(Scanner scanner, EmployerService employerService) {
        System.out.println("===Welcome to the Employee Menu!===");
        boolean running = true;
        while (running) {
            System.out.println("Choose your option:");
            System.out.println("1. Add an Employee");
            System.out.println("2. Remove an Employee");
            System.out.println("3. Get all Employees");
            System.out.println("4. Get Employees by Department");
            System.out.println("5. Sort by Hire Date");
            System.out.println("6. Update Employee");
            System.out.println("0. Back to Main Menu");

            int choice = readInt(scanner);

            switch (choice) {
                case 1 -> addEmployeeMenu(scanner, employerService);
                case 2 -> removeEmployeeMenu(scanner, employerService);
                case 3 -> employerService.getEmployees().forEach(System.out::println);
                case 4 -> getByDepartmentMenu(scanner, employerService);
                case 5 -> employerService.sortByHireDate().forEach(System.out::println);
                case 6 -> updateEmployeeMenu(scanner, employerService);
                case 0 -> running = false;
                default -> System.out.println("Invalid Option!");
            }
        }
    }

    private static void addEmployeeMenu(Scanner scanner, EmployerService employerService) {
        System.out.println("Name:");
        String name = readString(scanner);
        System.out.println("Email:");
        String email = readString(scanner);
        System.out.println("Phone Number:");
        String phone = readString(scanner);
        System.out.println("Department Name:");
        String deptName = readString(scanner);
        System.out.println("Department Description:");
        String deptDesc = readString(scanner);
        System.out.println("Salary:");
        int salary = readInt(scanner);
        System.out.println("Hire Date (YYYY-MM-DD):");
        String date = readString(scanner);
        Department department = new Department(deptName, deptDesc);
        Employee employee = new Employee(name, email, phone, department, salary, LocalDate.parse(date));
        employerService.addEmployee(employee);
        System.out.println("Employee added successfully!");
        AuditService.getInstance().log("add_employee");
        try{
            EmployeeJdbcService.getInstance().create(employee);
        } catch (SQLException e) {
            System.out.println("Error when trying to add employee: " + e);
        }
    }

    private static void removeEmployeeMenu(Scanner scanner, EmployerService employerService) {
        System.out.println("Enter the employee's email to remove:");
        String email = readString(scanner);
        try {
            EmployeeJdbcService.getInstance().delete(email);
            employerService.removeEmployee(email);
            System.out.println("Employee removed successfully!");
            AuditService.getInstance().log("remove_employee");
        } catch (SQLException e) {
            System.out.println("Error when trying to remove employee: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void getByDepartmentMenu(Scanner scanner, EmployerService employerService) {
        System.out.println("Enter the department name:");
        String dept = readString(scanner);
        employerService.getByDepartment(dept).forEach(System.out::println);
    }

    private static void updateEmployeeMenu(Scanner scanner, EmployerService employerService) {
        System.out.println("Enter the employee's email to update:");
        String email = readString(scanner);
        Employee e = employerService.searchByEmail(email);
        if (e == null) {
            System.out.println("Employee not found!");
            return;
        }
        System.out.println("New name:");
        String name = readString(scanner);
        System.out.println("New phone number:");
        String phone = readString(scanner);
        System.out.println("New department name:");
        String deptName = readString(scanner);
        System.out.println("New department description:");
        String deptDesc = readString(scanner);
        System.out.println("New salary:");
        int salary = readInt(scanner);
        System.out.println("New hire date (YYYY-MM-DD):");
        String date = readString(scanner);
        Department department = new Department(deptName, deptDesc);
        employerService.updateEmployee(e, name, email, phone, department, salary, LocalDate.parse(date));
        System.out.println("Employee updated successfully!");
        AuditService.getInstance().log("update_employee");
        try {
            EmployeeJdbcService.getInstance().update(e);
        } catch (SQLException error) {
            System.out.println("Error when trying to update employee: " + error);
        }
    }

    private static void customerMenu(Scanner scanner, CustomerService customerService) {
        System.out.println("===Welcome to the Customer Menu!===");
        boolean running = true;
        while (running) {
            System.out.println("Choose your option:");
            System.out.println("1. Add a Customer");
            System.out.println("2. Remove a Customer");
            System.out.println("3. Get all Customers");
            System.out.println("4. Search by Email");
            System.out.println("0. Back to Main Menu");

            int choice = readInt(scanner);

            switch (choice) {
                case 1 -> addCustomerMenu(scanner, customerService);
                case 2 -> removeCustomerMenu(scanner, customerService);
                case 3 -> customerService.getCustomers().forEach(System.out::println);
                case 4 -> searchCustomerMenu(scanner, customerService);
                case 0 -> running = false;
                default -> System.out.println("Invalid Option!");
            }
        }
    }

    private static void addCustomerMenu(Scanner scanner, CustomerService customerService) {
        System.out.println("Name:");
        String name = readString(scanner);
        System.out.println("Email:");
        String email = readString(scanner);
        System.out.println("Phone Number:");
        String phone = readString(scanner);
        System.out.println("Credit Score:");
        int creditScore = readInt(scanner);
        Customer c = new Customer(name, email, phone, creditScore);
        customerService.addCustomer(c);
        System.out.println("Customer added successfully!");
        AuditService.getInstance().log("add_customer");
        try {
            CustomerJdbcService.getInstance().create(c);
        } catch (SQLException e) {
            System.out.println("Error when trying to add customer: " + e);
        }
    }

    private static void removeCustomerMenu(Scanner scanner, CustomerService customerService) {
        System.out.println("Enter the customer's email to remove:");
        String email = readString(scanner);
        try {
            TransactionJdbcService.getInstance().deleteByCustomerEmail(email);
            CustomerJdbcService.getInstance().delete(email);
            customerService.removeCustomer(email);
            System.out.println("Customer removed successfully!");
            AuditService.getInstance().log("remove_customer");
        } catch (SQLException e) {
            System.out.println("Error when trying to remove customer: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void searchCustomerMenu(Scanner scanner, CustomerService customerService) {
        System.out.println("Enter the customer's email:");
        String email = readString(scanner);
        Customer c = customerService.searchByEmail(email);
        if (c == null) {
            System.out.println("Customer not found!");
            return;
        }
        System.out.println(c);
        AuditService.getInstance().log("customer_search");
    }
}