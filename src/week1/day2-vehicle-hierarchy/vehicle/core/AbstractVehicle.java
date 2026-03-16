package vehicle.core;

import vehicle.contract.Drivable;
import vehicle.model.VehicleStatus;

public abstract class AbstractVehicle implements Drivable {

    private final double mileAge;
    private final double fuelCapacity;
    private final double fuelCostPerKM;
    private final int seatCapacity;
    private final String name;
    private final String type;
    private VehicleStatus status;

    public AbstractVehicle(double mileAge, double fuelCapacity, int seatCapacity, String name, double fuelCostPerKM, String type) {
        this.mileAge = mileAge;
        this.fuelCapacity = fuelCapacity;
        this.seatCapacity = seatCapacity;
        this.status = VehicleStatus.ACTIVE;
        this.name = name;
        this.fuelCostPerKM = fuelCostPerKM;
        this.type = type;
    }

    protected double getMileAge() {return mileAge;}
    protected double getFuelCapacity() { return fuelCapacity;}
    protected int getSeatCapacity() { return seatCapacity; }
    public VehicleStatus getStatus() { return status; }
    public String getName() { return name; }
    protected String getType() { return type; }
    protected double getFuelCostPerKM() { return fuelCostPerKM; }


    public double getFuelCost(double km) {
        return fuelCostPerKM*km;
    } ;

    protected void updateStatus(VehicleStatus newStatus) {
        this.status = newStatus;
    }

    @Override
    public abstract void refuel() ;
}
