package vehicle.model;

public enum VehicleStatus {
    ACTIVE("Active"),
    MAINTENANCE("Maintenance"),
    RETIRED("Retired");

    private final String label;

    VehicleStatus(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
