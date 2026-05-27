package service;

import model.ElectricVehicle;
import model.HybridVehicle;
import model.Vehicle;
import model.CombustionVehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleJdbcService {
    private final Connection connection = DatabaseConnection.getConnection();
    private static VehicleJdbcService instance;

    private VehicleJdbcService () {}

    public static VehicleJdbcService getInstance() {
        if (instance == null) {
            instance = new VehicleJdbcService();
        }
        return instance;
    }

    public boolean create(Vehicle v) throws SQLException {
        String sql = "INSERT INTO vehicles (id, mileage, price, production_year, vehicle_body, vehicle_brand, vehicle_model, type, fuel_type, cc, fuel_consumption, energy_consumption) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, v.getId());
        pstmt.setInt(2, v.getMileage());
        pstmt.setInt(3, v.getPrice());
        pstmt.setInt(4, v.getProductionYear());
        pstmt.setString(5, v.getVehicleBody());
        pstmt.setString(6, v.getVehicleBrand());
        pstmt.setString(7, v.getVehicleModel());

        if (v instanceof CombustionVehicle cv) {
            pstmt.setString(8, "COMBUSTION");
            pstmt.setString(9, cv.getFuelType());
            pstmt.setInt(10, cv.getCc());
            pstmt.setDouble(11, cv.getFuelConsumption());
            pstmt.setNull(12, Types.DOUBLE);
        } else if (v instanceof ElectricVehicle ev) {
            pstmt.setString(8, "ELECTRIC");
            pstmt.setNull(9, Types.VARCHAR);
            pstmt.setNull(10, Types.INTEGER);
            pstmt.setNull(11, Types.DOUBLE);
            pstmt.setDouble(12, ev.getEnergyConsumption());
        } else if (v instanceof HybridVehicle hv) {
            pstmt.setString(8, "HYBRID");
            pstmt.setString(9, hv.getFuelType());
            pstmt.setInt(10, hv.getCc());
            pstmt.setDouble(11, hv.getMpg());
            pstmt.setDouble(12, hv.getEnergyConsumption());
        }
        return pstmt.executeUpdate() > 0;
    }

    public List<Vehicle> read() throws SQLException {
        List<Vehicle> returnedList = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String type = rs.getString("type");
            Vehicle v = switch (type) {
                case "COMBUSTION" -> {
                    CombustionVehicle cv = new CombustionVehicle(
                            rs.getInt("mileage"), rs.getInt("price"),
                            rs.getInt("production_year"), rs.getInt("cc"),
                            rs.getString("vehicle_body"), rs.getString("vehicle_brand"),
                            rs.getString("vehicle_model"), rs.getString("fuel_type"),
                            rs.getDouble("fuel_consumption"));
                    cv.setId(rs.getInt("id"));
                    yield cv;
                }
                case "ELECTRIC" -> {
                    ElectricVehicle ev = new ElectricVehicle(
                            rs.getInt("mileage"), rs.getInt("price"),
                            rs.getInt("production_year"), rs.getString("vehicle_body"),
                            rs.getString("vehicle_brand"), rs.getString("vehicle_model"),
                            rs.getDouble("energy_consumption"));
                    ev.setId(rs.getInt("id"));
                    yield ev;
                }
                case "HYBRID" -> {
                    CombustionVehicle combustionPart = new CombustionVehicle(
                            rs.getInt("mileage"), rs.getInt("price"),
                            rs.getInt("production_year"), rs.getInt("cc"),
                            rs.getString("vehicle_body"), rs.getString("vehicle_brand"),
                            rs.getString("vehicle_model"), rs.getString("fuel_type"),
                            rs.getDouble("fuel_consumption"));
                    ElectricVehicle electricPart = new ElectricVehicle(
                            rs.getInt("mileage"), rs.getInt("price"),
                            rs.getInt("production_year"), rs.getString("vehicle_body"),
                            rs.getString("vehicle_brand"), rs.getString("vehicle_model"),
                            rs.getDouble("energy_consumption"));
                    HybridVehicle hv = new HybridVehicle(combustionPart, electricPart);
                    hv.setId(rs.getInt("id"));
                    yield hv;
                }
                default -> throw new SQLException("Unknown vehicle type: " + type);
            };
            returnedList.add(v);
        }
        return returnedList;
    }

    public boolean update(Vehicle v) throws SQLException {
        String sql = "UPDATE vehicles SET mileage=?, price=?, production_year=?, vehicle_body=?, vehicle_brand=?, vehicle_model=?, fuel_type=?, cc=?, fuel_consumption=?, energy_consumption=? WHERE id=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, v.getMileage());
        pstmt.setInt(2, v.getPrice());
        pstmt.setInt(3, v.getProductionYear());
        pstmt.setString(4, v.getVehicleBody());
        pstmt.setString(5, v.getVehicleBrand());
        pstmt.setString(6, v.getVehicleModel());

        if (v instanceof CombustionVehicle cv) {
            pstmt.setString(7, cv.getFuelType());
            pstmt.setInt(8, cv.getCc());
            pstmt.setDouble(9, cv.getFuelConsumption());
            pstmt.setNull(10, Types.DOUBLE);
        } else if (v instanceof ElectricVehicle ev) {
            pstmt.setNull(7, Types.VARCHAR);
            pstmt.setNull(8, Types.INTEGER);
            pstmt.setNull(9, Types.DOUBLE);
            pstmt.setDouble(10, ev.getEnergyConsumption());
        } else if (v instanceof HybridVehicle hv) {
            pstmt.setString(7, hv.getFuelType());
            pstmt.setInt(8, hv.getCc());
            pstmt.setDouble(9, hv.getMpg());
            pstmt.setDouble(10, hv.getEnergyConsumption());
        }
        pstmt.setInt(11, v.getId());
        return pstmt.executeUpdate() > 0;
    }

    public boolean delete(int id) throws SQLException{
        String sql = "DELETE FROM vehicles WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);
        return pstmt.executeUpdate() > 0;
    }
}

