package studentdorm;

import java.util.Collections;
/**
 *
 * @author AS KHAN
 */
public class Building {
    /**
     * This class represents our student dorm building.
     */
    //add class attributes
    private final Person[] peopleOnFloor;
    private final Elevator elevator;

    //add constructors as described in the problem statement
//    TODO: Take into account that according to German law, buildings with fewer than 5 floors should not be allowed to have an elevator.
    public Building(int numberOfFloors) {
        this.peopleOnFloor = new Person[numberOfFloors];
        if (numberOfFloors < 5) {
            throw new IllegalArgumentException("Building must have at least 5 floors");
        } else {
            this.elevator = new Elevator(numberOfFloors - 1);
        }
    }

    public Building(int numberOfFloors, Elevator elevator) {
        this.peopleOnFloor = new Person[numberOfFloors];
        if (numberOfFloors - 1 > elevator.getMaxFloor()) {
            throw new IllegalArgumentException("Number of floors - 1 cannot be larger than the maximum floor of the elevator");
        } else {
            this.elevator = elevator;
        }
    }

    public Person[] getPeopleOnFloor() {
        return peopleOnFloor;
    }

    public Elevator getElevator() {
        return elevator;
    }

    /**
     * This method operates the elevator. It will move, open and close the doors as long as necessary in order to fulfill
     * every passenger request.
     */
    public void operateElevator() throws IllegalArgumentException {
        //implement the operateElevator method as described in the problem statement
        while (this.elevator.getDirection() != Direction.IDLE) {
            this.elevator.move();
            Person personIncoming = this.peopleOnFloor[this.elevator.getCurrentFloor()];
            this.elevator.openDoor(personIncoming);
            this.elevator.closeDoor();
        }
        //uncomment the following two lines for easier local testing
        this.elevator.printSequence();
        System.out.println("Elevator finished on " + this.elevator.getCurrentFloor());
    }


    /**
     * This method is used before operating the elevator. It basically prepares the stops which will be visited
     * by the elevator. People waiting on the floors are taken into consideration when filling the lists. The lists are then
     * used to create a new Stops object for elevator.Do not forget that the elevator always starts on floor 0, so the lists
     * have to be sorted accordingly. It is advisable for you to orient yourself on the SCAN algorithm.
     */
    public void processRequests() {
        for (Person person : this.peopleOnFloor) {
            if (person != null) {
                Direction direction = Direction.computeDirection(this.elevator.getCurrentFloor(), person.getDestinationFloor());
                if (direction == Direction.UP && !this.elevator.getStops().getStopsUp().contains(person.getDestinationFloor())) {
                    this.elevator.getStops().addStop(direction, person.getDestinationFloor());
                } else if (direction == Direction.DOWN && !this.elevator.getStops().getStopsDown().contains(person.getDestinationFloor())) {
                    this.elevator.getStops().addStop(direction, person.getDestinationFloor());
                }
            }
        }
        this.elevator.getStops().getStopsUp().sort(Integer::compareTo);
        this.elevator.getStops().getStopsDown().sort(Collections.reverseOrder());
//        System.out.println("Stops up: " + this.elevator.getStops().getStopsUp());
//        System.out.println("Stops down: " + this.elevator.getStops().getStopsDown());
    }

    /**
     * This method can be used to initialize some standard values in order to test the implementation locally.
     */
    public void setupPeopleWaiting() {
        this.peopleOnFloor[0] = new Person(2);
        this.peopleOnFloor[1] = new Person(0);
        this.peopleOnFloor[4] = new Person(3);
//        this.peopleOnFloor[3] = new Person(4);
    }



    //TODO: use the main method for local testing and debugging
    public static void main(String[] args) {
        Elevator elevator = new Elevator(15, 5);
        Building building = new Building(6, elevator);
//        System.out.println(building.getElevator().getMaxFloor());
        building.setupPeopleWaiting();
        building.processRequests();
        building.getElevator().setDirection(Direction.UP);
        building.operateElevator();
    }

}