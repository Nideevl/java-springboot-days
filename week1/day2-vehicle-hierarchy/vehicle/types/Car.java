package vehicle.types;

import vehicle.core.AbstractVehicle;

public class Car extends AbstractVehicle {

    private final String licensePlate;

    public Car(double mileAge, double fuelCapacity, int seatCapacity, String name, double fuelCostPerKM, String licensePlate){
        super(mileAge, fuelCapacity, seatCapacity, name, fuelCostPerKM, "Car");
        this.licensePlate = licensePlate;
    }

    protected String getLicensePlate() { return licensePlate; }

    @Override
    public void refuel() {
        System.out.println("Car with name "+getName()+" is refueling at Station");
    }
}

