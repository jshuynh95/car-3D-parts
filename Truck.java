class Truck extends Vehicle {
    public Truck(int time) {
        super(time);
    }

    @Override
    public String getType() {
        return "Truck";
    }
}
