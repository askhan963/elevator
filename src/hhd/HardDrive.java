package hdd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HardDrive {

    private static final int DISC_SIZE = 200;

    private final List<Integer> left;
    private final List<Integer> right;
    private final List<Integer> sequence;
    private final int head;
    private final RotationDirection rotationDirection;

    public HardDrive(int head, RotationDirection rotationDirection) {
        this.head = head;
        this.rotationDirection = rotationDirection;

        left = new ArrayList<>();
        right = new ArrayList<>();
        sequence = new ArrayList<>();

    }


    public static void main(String[] args) {

        //requests
        int[] requests = {176, 79, 34, 60, 92, 11, 41, 114};

        HardDrive hardDrive = new HardDrive(50, RotationDirection.LEFT);
        hardDrive.scan(requests);

        hardDrive = new HardDrive(50, RotationDirection.RIGHT);
        hardDrive.scan(requests);
    }

    void scan(int[] requests) {

        // end value of the side in the direction the disk is rotating needs to be visited
        // before reversing the rotationDirection
        if (rotationDirection == RotationDirection.LEFT) {
            left.add(0);
        } else {
            right.add(DISC_SIZE - 1);
        }

        //add requests to the lists
        //values smaller than the current position of the head are added to the left list and others to the right
        for (int element : requests) {
            if (element < head) {
                left.add(element);

            }
            if (element >= head) {
                right.add(element);
            }
        }

        // sorting left and right lists
        Collections.sort(left);
        Collections.reverse(left);

        Collections.sort(right);

        // run in both directions
        if (rotationDirection == RotationDirection.LEFT) {
            runLeft();
            runRight();
        } else {
            runRight();
            runLeft();
        }

        //print scan sequence
        printSequence();

    }

    public void runLeft() {
        //go through the disk in the left direction, get the 1. element from the left list that gets scanned.
        for (int i = head - 1; i >= 0; i--) {
            if (left.isEmpty()) {
                return;
            }

            int element = left.get(0);

            if (i == element) {
                sequence.add(element);
                left.remove(0);
            }
        }
    }


    //similar to runLeft, but in the right Direction
    public void runRight() {
        for (int i = head; i < DISC_SIZE; i++) {
            if (right.isEmpty()) {
                return;
            }

            int element = right.get(0);

            if (i == element) {
                sequence.add(element);
                right.remove(0);
            }
        }
    }

    private void printSequence() {
        for (Integer integer : sequence) {
            System.out.print(integer + "\n");
        }
    }


}
