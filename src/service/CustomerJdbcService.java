package service;

import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerJdbcService {
    private static CustomerJdbcService instance;
    private final Connection connection = DatabaseConnection.getConnection();

    private CustomerJdbcService() {}

    public static CustomerJdbcService getInstance() {
        if (instance == null) {
            instance = new CustomerJdbcService();
        }
        return instance;
    }

    public boolean create(Customer c) throws SQLException {
        String sql = "INSERT INTO customers (name, email, phone_number, credit_score) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, c.getName());
        pstmt.setString(2, c.getEmail());
        pstmt.setString(3, c.getPhoneNumber());
        pstmt.setInt(4, c.getCreditScore());
        return pstmt.executeUpdate() > 0;
    }

    public boolean delete(String email) throws SQLException {
        String sql = "DELETE FROM customers WHERE email = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, email);
        return pstmt.executeUpdate() > 0;
    }

    public boolean update(Customer c) throws SQLException {
        String sql = "UPDATE customers SET name = ?, phone_number = ?, credit_score = ? WHERE email = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, c.getName());
        pstmt.setString(2, c.getPhoneNumber());
        pstmt.setInt(3, c.getCreditScore());
        pstmt.setString(4, c.getEmail());
        return pstmt.executeUpdate() > 0;
    }

    public List<Customer> read() throws SQLException {
        List<Customer> returnedList = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            Customer c = new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
            returnedList.add(c);
        }
        return returnedList;
    }

    public Customer findByEmail(String email) throws SQLException{
        String sql = "SELECT * FROM customers WHERE email = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            return new Customer(rs.getString("name"), rs.getString("email"), rs.getString("phone_number"), rs.getInt("credit_score"));
        }
        return null;
    }

}
