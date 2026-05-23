package model;

public class ElectricVehicle extends Vehicle {
    private double energyConsumption;

    public ElectricVehicle(int mileage, int price, int productionYear,
                           String vehicleBody, String vehicleBrand, String vehicleModel, double energyConsumption) {
        super(mileage, price, productionYear, vehicleBody, vehicleBrand, vehicleModel);
        this.energyConsumption = energyConsumption;
    }

    public double getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(double energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    @Override
    public String toString() {
        return "\n=== ELECTRIC VEHICLE ===" +
                "\nID: " + id +
                "\nBrand: " + vehicleBrand +
                "\nModel: " + vehicleModel +
                "\nYear: " + productionYear +
                "\nMileage: " + mileage + " km" +
                "\nPrice: " + price + " USD" +
                "\nBody: " + vehicleBody +
                "\nEnergy Consumption: " + energyConsumption + " kWh/100km" +
                "\n===========================";
    }
}
