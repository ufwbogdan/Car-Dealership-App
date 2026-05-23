package model;
import java.time.LocalDate;
public class Employee extends Person implements Comparable<Employee> {
    private Department department;
    private int salary;
    private LocalDate hireDate;
    public Employee(String name, String email, String phoneNumber, Department department, int salary, LocalDate hiredate) {
        super(name, email, phoneNumber);
        this.department = department;
        this.salary = salary;
        this.hireDate = hiredate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return "Employee{" + super.toString() +
                "department=" + department +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                '}';
    }

    @Override
    public int compareTo(Employee other) {
        int salaryCompare = Integer.compare(this.salary, other.salary);
        if (salaryCompare != 0) return salaryCompare;
        return this.name.compareTo(other.name);
    }
}
