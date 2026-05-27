package model;

import java.time.LocalDateTime;

public class Transaction {
    private final Vehicle vehicle;
    private final Customer customer;
    private final int id;
    private final LocalDateTime date;

    public Transaction( int id, Vehicle vehicle, Customer customer, LocalDateTime date) {
        this.vehicle = vehicle;
        this.customer = customer;
        this.id = id;
        this.date = date;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "\n=== TRANSACTION ===" +
                "\nID: " + id +
                "\nVehicle: " + vehicle.getVehicleBrand() + " " + vehicle.getVehicleModel() +
                "\nCustomer: " + customer.getName() +
                "\nDate: " + date +
                "\n===================";
    }
}
