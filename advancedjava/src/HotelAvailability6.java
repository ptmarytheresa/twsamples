import java.util.concurrent.Executors;
import java.util.concurrent.Future;
//Virtual Threads (Java 21)

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

//Virtual threads are lightweight threads managed by the JVM.
//newVirtualThreadPerTaskExecutor() creates a new virtual thread per submitted task.
//They are particularly useful for I/O-intensive workloads.
//They do not automatically make CPU-intensive tasks faster

//// Start a single virtual thread
//Thread.startVirtualThread(() -> System.out.println("Hello"));
//
//// Or with the builder
//Thread t = Thread.ofVirtual().start(() -> doWork());
//
//// Most common: one virtual thread per task
//try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
//        for (int i = 0; i < 100_000; i++) {
//        executor.submit(() -> {
//        Thread.sleep(1000);   // blocks cheaply
//            return "done";
//                    });
//                    }
//                    }