import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Please, enter number of floors in the building: ");
        Integer numberOfFloors = Util.readAndValidateInt(5, 20);
        System.out.println("Please, enter height of the floor: ");
        Integer heightOfFloor = Util.readAndValidateInt(1, null);
        System.out.println("Please, enter speed of elevator: ");
        Integer elevatorSpeed = Util.readAndValidateInt(1, null);
        System.out.println("Please, enter time between doors opening and closing: ");
        Integer timeBtwDoorOpenClose = Util.readAndValidateInt(1, null);
        Elevator elevator = new Elevator(numberOfFloors, heightOfFloor, elevatorSpeed, timeBtwDoorOpenClose);
        System.out.println("To call lift, please enter the number of floor on which you are: ");

        new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    if (System.in.available() != 0) {
                        Integer input;
                        synchronized (elevator) {
                            input = Util.readAndValidateInt(1, numberOfFloors);
                        }
                        elevator.getActions().put(input);
                    }
                } catch (IOException e) {
                    System.out.println("Impossible to read from console");
                    System.exit(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
        new Thread(elevator::startMoving).start();
    }
}
