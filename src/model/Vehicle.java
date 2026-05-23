package model;

public abstract class Vehicle {
    protected int id;
    protected int mileage;
    protected int price;
    protected int productionYear;
    protected String vehicleBody;
    protected String vehicleBrand;
    protected String vehicleModel;

    public Vehicle(int mileage, int price, int productionYear, String vehicleBody, String vehicleBrand, String vehicleModel) {
        this.mileage = mileage;
        this.price = price;
        this.productionYear = productionYear;
        this.vehicleBody = vehicleBody;
        this.vehicleBrand = vehicleBrand;
        this.vehicleModel = vehicleModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public String getVehicleBody() {
        return vehicleBody;
    }

    public void setVehicleBody(String vehicleBody) {
        this.vehicleBody = vehicleBody;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id + ", " +
                "mileage=" + mileage +
                ", productionYear=" + productionYear +
                ", vehicleBody='" + vehicleBody + '\'' +
                ", vehicleBrand='" + vehicleBrand + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                '}';
    }
}
