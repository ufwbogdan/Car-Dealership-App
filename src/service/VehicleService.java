package service;
import model.Customer;
import model.Transaction;
import model.Vehicle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class VehicleService {
    private static VehicleService instance;
    private final List<Vehicle> vehicles = new ArrayList<>();
    private final List<Vehicle> soldVehicles = new ArrayList<>();

    private VehicleService() {}

    public static VehicleService getInstance() {
        if(instance == null) {
            instance = new VehicleService();
        }
        return instance;
    }

    public void addVehicle(Vehicle v) {
        if(v == null) {
            throw new IllegalArgumentException("The Vehicle cannot be null!");
        }
        v.setId(vehicles.size() + 1);
        vehicles.add(v);
    }

    public void removeVehicle(int id) {
        if (searchById(id) == null) {
            throw new IllegalArgumentException("Vehicle with id " + id + " not found!");
        }
        vehicles.removeIf(v -> v.getId() == id);
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<Vehicle> getSoldVehicles() {
        return soldVehicles;
    }

    public List<Vehicle> searchByYear(int year) {
        return vehicles.stream()
                .filter(v -> v.getProductionYear() == year)
                .collect(Collectors.toList());
    }

    public List<Vehicle> searchByBrand(String brand) {
        if (brand == null) {
            throw new IllegalArgumentException("Vehicle brand must not be null!");
        }
        return vehicles.stream()
                .filter(v -> v.getVehicleBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());

    }

    public Vehicle searchById(int id) {
        return vehicles.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Vehicle> searchByBrandAndModel(String brand, String model) {
        return vehicles.stream()
                .filter(v -> v.getVehicleBrand().equalsIgnoreCase(brand)
                        && v.getVehicleModel().equalsIgnoreCase(model))
                .collect(Collectors.toList());
    }

    public List<Vehicle> filterByMaxMileage(int mileage) {
        return vehicles.stream()
                .filter(v -> v.getMileage() <= mileage)
                .collect(Collectors.toList());
    }

    public List<Vehicle> filterByCarBody(String carBody) {
        return vehicles.stream()
                .filter(v -> v.getVehicleBody().equalsIgnoreCase(carBody))
                .collect(Collectors.toList());
    }

    public List<Vehicle> filterByYear(int yearMin, int yearMax) {
        int year = LocalDate.now().getYear();
        if (yearMin < 1900 || yearMax > year || yearMax < yearMin) {
            throw new IllegalArgumentException("The years entered are invalid!");
        }
        return vehicles.stream()
                .filter(v -> v.getProductionYear() <= yearMax && v.getProductionYear() >= yearMin)
                .collect(Collectors.toList());
    }

    public List<Vehicle> sortByMileage() {
        return vehicles.stream()
                .sorted(Comparator.comparingInt(Vehicle::getMileage))
                .collect(Collectors.toList());
    }

    public void updateVehicle(Vehicle v, int newMileage, int newPrice, String newVehicleBody, String newVehicleBrand, String newVehicleModel) {
        v.setMileage(newMileage);
        v.setPrice(newPrice);
        v.setVehicleBody(newVehicleBody);
        v.setVehicleBrand(newVehicleBrand);
        v.setVehicleModel(newVehicleModel);
    }

    public double computeFinancing(Vehicle v, int months, double interestRate) {
        double monthlyRate = interestRate / 12 / 100;
        return (v.getPrice() * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));
    }

    public void sellVehicle(int id, Customer c) {
        Vehicle v = searchById(id);
        if (v == null) {
            throw new IllegalArgumentException("Vehicle with id " + id + " not found!");
        }
        TransactionService transactionService = TransactionService.getInstance();
        int transactionId = transactionService.getTransactionList().size() + 1;
        Transaction transaction = new Transaction(transactionId, v, c, LocalDateTime.now());
        transactionService.addTransaction(transaction);

        soldVehicles.add(v);
        vehicles.removeIf(vehicle -> vehicle.getId() == id);
        System.out.println("Sale completed!" +
                "\nVehicle: " + v.getVehicleBrand() + " " + v.getVehicleModel() +
                "\nPrice: " + v.getPrice() +
                "\nBuyer: " + c.getName() +
                "\nPhone Number: " + c.getPhoneNumber());
    }
}
