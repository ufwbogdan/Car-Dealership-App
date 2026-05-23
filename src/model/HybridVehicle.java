package model;

public class HybridVehicle extends Vehicle {
    final private CombustionVehicle combustionPart;
    final private ElectricVehicle electricPart;

    public HybridVehicle(CombustionVehicle combustionPart, ElectricVehicle electricPart) {
        super(combustionPart.getMileage(), combustionPart.getPrice(),
                combustionPart.getProductionYear(), combustionPart.getVehicleBody(),
                combustionPart.getVehicleBrand(), combustionPart.getVehicleModel());
        this.combustionPart = combustionPart;
        this.electricPart = electricPart;
    }

    public String getFuelType() { return combustionPart.getFuelType(); }
    public double getMpg() { return combustionPart.getMpg(); }
    public int getCc() { return combustionPart.getCc(); }
    public double getEnergyConsumption() { return electricPart.getEnergyConsumption(); }

    public CombustionVehicle getCombustionPart() { return combustionPart; }
    public ElectricVehicle getElectricPart() { return electricPart; }

    @Override
    public String toString() {
        return "\n=== HYBRID VEHICLE ===" +
                "\nID: " + id +
                "\nBrand: " + vehicleBrand +
                "\nModel: " + vehicleModel +
                "\nYear: " + productionYear +
                "\nMileage: " + mileage + " km" +
                "\nPrice: " + price + " USD" +
                "\nBody: " + vehicleBody +
                "\nFuel Type: " + getFuelType() +
                "\nEngine Capacity: " + getCc() + " cc" +
                "\nFuel Consumption: " + getMpg() + " L/100km" +
                "\nEnergy Consumption: " + getEnergyConsumption() + " kWh/100km" +
                "\n===========================";
    }
}