package studentdorm;

import java.util.ArrayList;
import java.util.List;

public class Stops {

    /**
     * This class basically acts like a wrapper class that holds two lists representing stops in upwards and downwards
     * direction.
     *
     */
    private List<Integer> stopsUp;
    private List<Integer> stopsDown;

    public Stops(List<Integer> stopsUp, List<Integer> stopsDown) {
        this.stopsUp = stopsUp;
        this.stopsDown = stopsDown;
    }

    public Stops() {
        stopsUp = new ArrayList<>();
        stopsDown = new ArrayList<>();
    }

    //add Error Handling to each method

    /**
     * This method takes a direction value and based on that value it checks if the corresponding list is empty.
     */
    public boolean isEmpty(Direction direction) throws IllegalArgumentException {
        switch (direction) {
            case UP:
                return this.stopsUp.size() == 0;
            case DOWN:
                return this.stopsDown.size() == 0;
            default:
                throw new IllegalArgumentException("Direction must be UP or DOWN");
        }
    }

    /**
     *
     * This method removes a stop from one of the two lists. Which stop will be removed, is based on the direction and
     * int that are passed within in the parameters. The direction value determines the list from which to remove and
     * the int value determines the position within the list to remove from.
     *
     */
    public void remove(Direction direction, int index) throws IllegalArgumentException {
        if (direction == Direction.IDLE) {
            throw new IllegalArgumentException("Direction cannot be IDLE");
        } else if (index < 0) {
            throw new IllegalArgumentException("Index out of bounds");
        } else if (direction == Direction.UP) {
            if (index >= this.stopsUp.size()) {
                throw new IllegalArgumentException("Index out of bounds");
            } else {
                this.stopsUp.remove(index);
            }
        } else {
            if (index >= this.stopsDown.size()) {
                throw new IllegalArgumentException("Index out of bounds");
            } else {
                this.stopsDown.remove(index);
            }
        }
    }

    /**
     *
     * This method adds a stop to one of the two lists.The direction value determines which list to add to and the int
     * value represents the floor number which should be added.
     *
     */
    public void addStop(Direction direction, int floor) throws IllegalArgumentException {
        if (floor < 0) {
            throw new IllegalArgumentException("floor number is illegal");
        }
        switch (direction) {
            case IDLE -> throw new IllegalArgumentException("Direction cannot be IDLE");
            case UP -> this.stopsUp.add(floor);
            case DOWN -> this.stopsDown.add(floor);
            default -> throw new IllegalArgumentException("Direction must be UP or DOWN");
        }
    }


    /**
     * This method takes a direction value as well as an int value. Based on the direction value the method chooses the
     * list to retrieve the value from and the int value acts as an index indicating the position within the list to get the value from.
     *
     * @return an int value representing a floor number
     */
    public int getNextStop(Direction direction, int i) throws IllegalArgumentException {
        if (i < 0) {
            throw new IllegalArgumentException("Index out of bounds");
        } else {
            switch (direction) {
                case IDLE -> throw new IllegalArgumentException("Direction cannot be IDLE");
                case UP -> {
                    if (this.stopsUp.isEmpty() || i >= this.stopsUp.size()) {
                        throw new IllegalArgumentException("Index out of bounds");
                    } else {
                        return this.stopsUp.get(i);
                    }
                }
                case DOWN -> {
                    if (this.stopsDown.isEmpty() || i >= this.stopsDown.size()) {
                        throw new IllegalArgumentException("Index out of bounds");
                    } else {
                        return this.stopsDown.get(i);
                    }
                }
                default -> throw new IllegalArgumentException("Direction must be UP or DOWN");
            }
        }
    }

    public List<Integer> getStopsUp() {
        return stopsUp;
    }

    public void setStopsUp(List<Integer> stopsUp) {
        this.stopsUp = stopsUp;
    }

    public List<Integer> getStopsDown() {
        return stopsDown;
    }

    public void setStopsDown(List<Integer> stopsDown) {
        this.stopsDown = stopsDown;
    }
}