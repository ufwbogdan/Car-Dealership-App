package service;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleCsvService {
    private static VehicleCsvService instance;
    private static final String FILE_PATH = "vehicles.csv";
    private final CsvService csvService = CsvService.getInstance();

    private VehicleCsvService() {}

    public static VehicleCsvService getInstance() {
        if (instance == null) {
            instance = new VehicleCsvService();
        }
        return instance;
    }

    public void saveVehicles(List<Vehicle> vehicles) {
        List<String> lines = new ArrayList<>();
        for (Vehicle v : vehicles) {
            if (v instanceof CombustionVehicle cv) {
                lines.add("COMBUSTION," + cv.getId() + "," + cv.getMileage() + "," + cv.getPrice() + "," +
                        cv.getProductionYear() + "," + cv.getVehicleBody() + "," + cv.getVehicleBrand() + "," +
                        cv.getVehicleModel() + "," + cv.getFuelType() + "," + cv.getCc() + "," + cv.getFuelConsumption());
            } else if (v instanceof ElectricVehicle ev) {
                lines.add("ELECTRIC," + ev.getId() + "," + ev.getMileage() + "," + ev.getPrice() + "," +
                        ev.getProductionYear() + "," + ev.getVehicleBody() + "," + ev.getVehicleBrand() + "," +
                        ev.getVehicleModel() + "," + ev.getEnergyConsumption());
            } else if (v instanceof HybridVehicle hv) {
                lines.add("HYBRID," + hv.getId() + "," + hv.getMileage() + "," + hv.getPrice() + "," +
                        hv.getProductionYear() + "," + hv.getVehicleBody() + "," + hv.getVehicleBrand() + "," +
                        hv.getVehicleModel() + "," + hv.getFuelType() + "," + hv.getCc() + "," +
                        hv.getMpg() + "," + hv.getEnergyConsumption());
            }
        }
        csvService.writeToFile(FILE_PATH, lines);
    }

    public List<Vehicle> loadVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        List<String> lines = csvService.readFromFile(FILE_PATH);
        for (String line : lines) {
            String[] parts = line.split(",");
            switch (parts[0]) {
                case "COMBUSTION" -> {
                    CombustionVehicle cv = new CombustionVehicle(
                            Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[9]),
                            parts[5], parts[6], parts[7], parts[8], Double.parseDouble(parts[10]));
                    cv.setId(Integer.parseInt(parts[1]));
                    vehicles.add(cv);
                }
                case "ELECTRIC" -> {
                    ElectricVehicle ev = new ElectricVehicle(
                            Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), parts[5], parts[6], parts[7],
                            Double.parseDouble(parts[8]));
                    ev.setId(Integer.parseInt(parts[1]));
                    vehicles.add(ev);
                }
                case "HYBRID" -> {
                    CombustionVehicle combustionPart = new CombustionVehicle(
                            Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[9]),
                            parts[5], parts[6], parts[7], parts[8], Double.parseDouble(parts[10]));
                    ElectricVehicle electricPart = new ElectricVehicle(
                            Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), parts[5], parts[6], parts[7],
                            Double.parseDouble(parts[11]));
                    HybridVehicle hv = new HybridVehicle(combustionPart, electricPart);
                    hv.setId(Integer.parseInt(parts[1]));
                    vehicles.add(hv);
                }
            }
        }
        return vehicles;
    }
}