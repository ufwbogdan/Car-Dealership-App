package service;

import model.Department;
import model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeJdbcService {
    private final Connection connection = DatabaseConnection.getConnection();
    private static EmployeeJdbcService instance;

    private EmployeeJdbcService() {}

    public static EmployeeJdbcService getInstance() {
        if (instance == null) {
            instance = new EmployeeJdbcService();
        }
        return instance;
    }

    public boolean create(Employee e) throws SQLException {
        String sql = "INSERT INTO employees (name, email, phone_number, department_name, department_description, salary, hire_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, e.getName());
        pstmt.setString(2, e.getEmail());
        pstmt.setString(3, e.getPhoneNumber());
        pstmt.setString(4, e.getDepartment().getName());
        pstmt.setString(5, e.getDepartment().getDescription());
        pstmt.setInt(6, e.getSalary());
        pstmt.setDate(7, java.sql.Date.valueOf(e.getHireDate()));
        return pstmt.executeUpdate() > 0;
    }

    public List<Employee> read() throws SQLException {
        List<Employee> returnedList = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Department department = new Department(rs.getString("department_name"), rs.getString("department_description"));
            Employee e = new Employee(rs.getString("name"), rs.getString("email"), rs.getString("phone_number"),
                    department, rs.getInt("salary"), rs.getDate("hire_date").toLocalDate());
            returnedList.add(e);
        }
        return returnedList;
    }

    public boolean update (Employee e) throws SQLException {
        String sql = "UPDATE employees SET name = ?, phone_number = ?, department_name = ?, department_description = ?, salary = ?, hire_date = ? WHERE email = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, e.getName());
        pstmt.setString(2, e.getPhoneNumber());
        pstmt.setString(3, e.getDepartment().getName());
        pstmt.setString(4, e.getDepartment().getDescription());
        pstmt.setInt(5, e.getSalary());
        pstmt.setDate(6, java.sql.Date.valueOf(e.getHireDate()));
        pstmt.setString(7, e.getEmail());
        return pstmt.executeUpdate() > 0;
    }

    public boolean delete (String email) throws SQLException {
        String sql = "DELETE FROM employees WHERE email = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, email);
        return pstmt.executeUpdate() > 0;
    }
}
