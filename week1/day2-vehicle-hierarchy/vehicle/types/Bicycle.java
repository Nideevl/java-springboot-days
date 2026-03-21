    package vehicle.types;

    import vehicle.core.AbstractVehicle;

    public class Bicycle extends AbstractVehicle {
        private int gearCount;

        public Bicycle(double mileAge, int seatCapacity, String name, int gearCount){
            super(mileAge, 0, seatCapacity, name, 0, "Bicycle");
            this.gearCount = gearCount;
        }

        protected int getGearCount() { return gearCount; }

        @Override
        public void refuel() {
            System.out.println("Bicycles don't need to fuel");
        }
    }
