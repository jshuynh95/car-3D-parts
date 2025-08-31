abstract class Vehicle {
    protected int arrivalTime;

    public Vehicle(int time) {
        this.arrivalTime = time;
    }

    public abstract String getType();
}

class Car extends Vehicle {
    public Car(int time) {
        super(time);
    }

    public String getType() {
        return "Car";
    }
}
