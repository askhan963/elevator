package studentdorm;

public class Person {

    /**
     * This class will be used to represent the people waiting on the floors of the building as well as then
     * representing passengers on the elevator.
     */
    private final String name;
    private final int destinationFloor;
     static int counter;

    public Person(String name, int destinationFloor) {
        this.name = name;
        this.destinationFloor = destinationFloor;
        counter++;
    }

    public Person(int destinationFloor) {
        name = "P" + counter;
        this.destinationFloor = destinationFloor;
        counter++;
    }

    /**
     * @return the absolute distance between the current floor and the destinationFloor
     */
    public int computeDistance(int currentFloor) {
        return Math.abs(currentFloor - destinationFloor);
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public String getName() {
        return name;
    }
}
