import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HotelAvailability6 {

    public static void checkRoomAvailability(int roomNumber) {

        System.out.println(
                "Checking room: " + roomNumber
                        + " | Thread: " + Thread.currentThread()
        );

        // Simulate a database or external service call
        try {
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Room " + roomNumber + " is available");
    }

    public static void main(String[] args) throws Exception {

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            Future<?> room201 = executor.submit(
                    () -> checkRoomAvailability(201));

            Future<?> room202 = executor.submit(
                    () -> checkRoomAvailability(202));

            Future<?> room203 = executor.submit(
                    () -> checkRoomAvailability(203));

            room201.get();
            room202.get();
            room203.get();
        }
    }
}