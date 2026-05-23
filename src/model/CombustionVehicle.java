package model;

public class CombustionVehicle extends Vehicle {
    private String fuelType;
    private int cc;
    private double fuelConsumption;

    public CombustionVehicle(int mileage, int price,int productionYear, int cc, String vehicleBody,
                             String vehicleBrand, String vehicleModel, String fuelType, double fuelConsumption) {
        super(mileage, price, productionYear, vehicleBody, vehicleBrand, vehicleModel);
        this.fuelType = fuelType;
        this.fuelConsumption = fuelConsumption;
        this.cc = cc;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public double getMpg() {
        return fuelConsumption;
    }

    public void setMpg(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public String toString() {
        return "\n=== COMBUSTION VEHICLE ===" +
                "\nID: " + id +
                "\nBrand: " + vehicleBrand +
                "\nModel: " + vehicleModel +
                "\nYear: " + productionYear +
                "\nMileage: " + mileage + " km" +
                "\nPrice: " + price + " USD" +
                "\nBody: " + vehicleBody +
                "\nFuel Type: " + fuelType +
                "\nEngine Capacity: " + cc + " cc" +
                "\nFuel Consumption: " + fuelConsumption + " L/100km" +
                "\n===========================";
    }
}
