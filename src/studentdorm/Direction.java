package studentdorm;

public enum Direction {
    /**
     * The values of this enum are an important part of operating the elevator by stating its current direction.
     */
    IDLE, DOWN, UP;

    /**
     * This method is used to compute a direction value depending on the input values current floor and destination
     * floor.
     *
     * @return a direction value based on the computation
     */
    public static Direction computeDirection(int currentFloor, int destinationFloor) {
        if (currentFloor <= destinationFloor) {
            return UP;
        }

        return DOWN;
    }

    public static Direction getReverseDirection(Direction direction) {
        if (direction == IDLE) {
            return IDLE;
        } else if (direction == DOWN) {
            return UP;
        }

        return DOWN;
    }
}
