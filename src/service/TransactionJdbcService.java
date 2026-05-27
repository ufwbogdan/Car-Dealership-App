package service;

import model.CombustionVehicle;
import model.Customer;
import model.Transaction;
import model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionJdbcService {
    private final Connection connection = DatabaseConnection.getConnection();
    private static TransactionJdbcService instance;

    private TransactionJdbcService() {}

    public static TransactionJdbcService getInstance() {
        if (instance == null) {
            instance = new TransactionJdbcService();
        }
        return instance;
    }

    public boolean create(Transaction t) throws SQLException {
        String sql = "INSERT INTO transactions (id, vehicle_brand, vehicle_model, vehicle_price, customer_email, date) VALUES (?, ?, ?, ?, ?, ?)";        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, t.getId());
        pstmt.setString(2, t.getVehicle().getVehicleBrand());
        pstmt.setString(3, t.getVehicle().getVehicleModel());
        pstmt.setInt(4, t.getVehicle().getPrice());
        pstmt.setString(5, t.getCustomer().getEmail());
        pstmt.setTimestamp(6, java.sql.Timestamp.valueOf(t.getDate()));
        return pstmt.executeUpdate() > 0;
    }

    public List<Transaction> read() throws SQLException {
        List<Transaction> returnedList = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Vehicle vehicle = new CombustionVehicle(0, rs.getInt("vehicle_price"), 0, 0, "",
                    rs.getString("vehicle_brand"), rs.getString("vehicle_model"), "", 0);

            Customer customer = CustomerJdbcService.getInstance().findByEmail(rs.getString("customer_email"));

            Transaction t = new Transaction(rs.getInt("id"), vehicle, customer,
                    rs.getTimestamp("date").toLocalDateTime());
            returnedList.add(t);
        }
        return returnedList;
    }

    public boolean deleteByCustomerEmail(String email) throws SQLException {
        String sql = "DELETE FROM transactions WHERE customer_email = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, email);
        return pstmt.executeUpdate() >= 0;
    }
}
