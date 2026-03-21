package vehicle.types;

import vehicle.core.AbstractVehicle;

public class Truck extends AbstractVehicle {

    private final double cargoCapacity;

    public Truck(double mileAge, double fuelCapacity, int seatCapacity, String name, double fuelCostPerKM, double cargoCapacity){
        super(mileAge, fuelCapacity, seatCapacity, name, fuelCostPerKM, "Truck");
        this.cargoCapacity = cargoCapacity;
    }

    protected double getCargoCapacity() { return cargoCapacity; }

    @Override
    public void refuel() {
        System.out.println("Truck with name "+getName()+" is refueling at Station");
    }
}
