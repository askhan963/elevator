package studentdorm;

import java.util.*;
/**
 *
 * @author AS KHAN
 */
public class Elevator {

    private final List<Person> passengers;
    private Direction direction;
    private Stops stops;
    private final List<Integer> sequence;
    private final int capacity;
    private final int maxFloor;
    private int currentFloor;
    private static final int DEFAULT_CAPACITY = 15;

    public Elevator(int capacity, int maxFloor) {
        this.passengers = new ArrayList<>();
        this.direction = Direction.IDLE;
        this.stops = new Stops();
        this.sequence = new ArrayList<>();
        if (capacity < DEFAULT_CAPACITY) {
            throw new IllegalArgumentException("illegal capacity");
        } else {
            this.capacity = capacity;
        }
        this.maxFloor = maxFloor;
        this.currentFloor = 0;
    }

    public Elevator(int maxFloor) {
        this.passengers = new ArrayList<>();
        this.direction = Direction.IDLE;
        this.stops = new Stops();
        this.sequence = new ArrayList<>();
        this.capacity = DEFAULT_CAPACITY;
        this.maxFloor = maxFloor;
        this.currentFloor = 0;
    }

    public List<Person> getPassengers() {
        return passengers;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Stops getStops() {
        return stops;
    }

    public void setStops(Stops stops) {
        this.stops = stops;
    }

    public List<Integer> getSequence() {
        return sequence;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getDefaultCapacity() {
        return DEFAULT_CAPACITY;
    }

    public void move() {
        while (!changeFloor()) {
            if (changeFloor()) {
                this.sequence.add(this.currentFloor);
                break;
            }
        }
        this.sequence.add(this.currentFloor);
    }

//  returns if the elevator has a stop on the current floor.
    public boolean changeFloor() {
        switch (this.direction) {
            case IDLE -> {
                if (!this.stops.isEmpty(Direction.UP)) {
                    this.direction = Direction.UP;
                } else if (!this.stops.isEmpty(Direction.DOWN)) {
                    this.direction = Direction.DOWN;
                }
            }
            case UP -> this.currentFloor++;
            case DOWN -> this.currentFloor--;
            default -> throw new IllegalStateException("Illegal change: " + this.direction);
        }
        return stops.getNextStop(this.direction, 0) == this.currentFloor;
    }


    public boolean openDoor(Person person) {
        Comparator<Person> comparator = (p1, p2) -> {
            int p1Distance = p1.computeDistance(this.currentFloor);
            int p2Distance = p2.computeDistance(this.currentFloor);
            return p1Distance - p2Distance;
        };
        this.passengers.sort(comparator);
        while (this.passengers.size() > this.capacity) {
            this.passengers.remove(0);
        }
        ListIterator<Person> iterator = this.passengers.listIterator();
            while (iterator.hasNext()) {
                Person currentPassanger = iterator.next();
                if (currentPassanger.getDestinationFloor() == this.currentFloor) {
//                    iterator.remove();
                    this.passengers.remove(currentPassanger);
                    this.stops.remove(this.direction, 0);
                }
            }
        if (person != null) {
            Direction directionIncomingPerson = Direction.computeDirection(this.currentFloor, person.getDestinationFloor());
            if (this.passengers.size() < this.capacity) {
                this.passengers.add(person);
                this.stops.addStop(directionIncomingPerson, person.getDestinationFloor());
                return true;
            } else if (this.passengers.size() == this.capacity) {
                ListIterator<Person> iterator2 = this.passengers.listIterator();
                while (iterator2.hasNext()) {
                    Person currentPassanger = iterator2.next();
                    if (currentPassanger.computeDistance(this.currentFloor) < person.computeDistance(this.currentFloor)) {
                        iterator2.remove();
//                        this.passengers.remove(currentPassanger);
                        this.stops.remove(this.direction, 0);
                        this.passengers.add(person);
                        this.stops.addStop(directionIncomingPerson, person.getDestinationFloor());
                        return true;
                    } else if (currentPassanger.computeDistance(this.currentFloor) == person.computeDistance(this.currentFloor) && Direction.computeDirection(this.currentFloor, currentPassanger.getDestinationFloor()) == Direction.DOWN) {
                        iterator2.remove();
//                        this.passengers.remove(currentPassanger);
                        this.stops.remove(this.direction, 0);
                        this.passengers.add(person);
                        this.stops.addStop(directionIncomingPerson, person.getDestinationFloor());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void closeDoor() {
//        checked if the elevator visited all stops in the stops lists
        if (this.stops.isEmpty(Direction.UP) && this.stops.isEmpty(Direction.DOWN)) {
            this.direction = Direction.IDLE;
        } else if (this.direction == Direction.UP && (this.stops.isEmpty(Direction.UP) || this.currentFloor == this.maxFloor)) {
            this.direction = Direction.getReverseDirection(this.direction);
        } else if (this.direction == Direction.DOWN && (this.stops.isEmpty(Direction.DOWN) || this.currentFloor == 0)) {
            this.direction = Direction.getReverseDirection(this.direction);
        }
    }

    public void printSequence() {
        for (Integer number : this.sequence) {
            System.out.println(number);
        }
    }
}