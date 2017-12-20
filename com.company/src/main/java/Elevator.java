import java.util.concurrent.*;

public class Elevator {
    private BlockingQueue<Integer> actions = new LinkedBlockingQueue<>(10);

    private final Integer numberOfFloors;

    private final Integer timeBtwDoorOpenClose;

    private Integer currentFloor = 1;

    private final Long interval;

    public Elevator(Integer numberOfFloors, Integer heightOfFloor, Integer elevatorSpeed, Integer timeBtwDoorOpenClose) {
        this.numberOfFloors = numberOfFloors;
        this.timeBtwDoorOpenClose = timeBtwDoorOpenClose;
        this.interval = heightOfFloor * 1000L/elevatorSpeed ;
    }

    public BlockingQueue<Integer> getActions() {
        return actions;
    }

    public void startMoving() {
        while (!Thread.interrupted()) {
            try {
                moveToFloor(actions.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void moveToFloor(Integer floorNum) throws InterruptedException {
        moveBtwFloors(currentFloor, floorNum);
        openCloseDoors();
        Integer targetFloor = askFloor();
        movePassenger(currentFloor, targetFloor);
    }

    private void movePassenger(Integer currFloor, Integer targetFloor) throws InterruptedException {
        moveBtwFloors(currFloor, targetFloor);
        openCloseDoors();
    }

    private Integer askFloor() {
        synchronized (this) {
            System.out.println("Enter floor: ");
            return Util.readAndValidateInt(1, numberOfFloors);
        }
    }

    private void openCloseDoors() throws InterruptedException {
        System.out.println("Opening the doors");
        Thread.sleep(timeBtwDoorOpenClose * 1000);
        System.out.println("Closing the doors");
    }

    private void moveBtwFloors(Integer currFloor, Integer targetFloor) throws InterruptedException {
        if (currFloor > targetFloor) {
            for (int i = currFloor; i > targetFloor; i--) {
                Thread.sleep(interval);
                processFloor(i);
                currentFloor--;
            }
        } else {
            for (int i = currFloor; i < targetFloor; i++) {
                Thread.sleep(interval);
                processFloor(i);
                currentFloor++;
            }
        }
    }

    private void processFloor(Integer floorNumber) {
        System.out.println("Going through " + floorNumber + " floor");
    }
}
