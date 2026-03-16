package vehicle;

import vehicle.contract.Drivable;
import vehicle.core.AbstractVehicle;
import vehicle.model.VehicleStatus;
import vehicle.types.Bicycle;
import vehicle.types.Car;
import vehicle.types.Truck;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String [] args) {
        List<Drivable> vehicles = List.of(
                new Car(15, 45, 5, "Swift", 0.12, "MH12AB1234"),
                new Car(18, 50, 5, "City", 0.13, "MH12XY5678"),
                new Truck(8, 120, 2, "TataTruck", 0.30, 5000),
                new Truck(7, 150, 2, "AshokLeyland", 0.32, 7000),
                new Bicycle(0, 1, "MountainBike", 18)
        );

        for(Drivable vehicle : vehicles){
            vehicle.refuel();
        }

        Map<VehicleStatus, List<Drivable>> grouped = vehicles.stream()
                .collect(Collectors.groupingBy(v->((AbstractVehicle) v).getStatus()));

        vehicles.stream()
                .filter(v->((AbstractVehicle) v).getStatus() == VehicleStatus.ACTIVE)
                .sorted(Comparator.comparingDouble(v -> v.getFuelCost(1)))
                .forEach(v -> System.out.println(
                        ((AbstractVehicle)v).getName()+" | Fuel cost for 100km: "+ v.getFuelCost(100)
                ));

        grouped.forEach((status, list)->{
            System.out.println(status.getLabel() +":");
            list.forEach(v-> System.err.println("- "+((AbstractVehicle) v).getName()));
        });
    }
}
