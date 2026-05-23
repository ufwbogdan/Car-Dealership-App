package model;

public class Department {
    private String name;
    private String description;
    public Department(String name, String description) {
        this.name = name;
        this.description = description;
    }
    // getters
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    // setters
    public void setDescription(String description) {
        this.description = description;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Department{name = '" + name + "', description = '" + description + "'}";
    }
}
