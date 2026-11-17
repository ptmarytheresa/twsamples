import java.util.ArrayList;
import java.util.List;
//Sequenced Collections (Java 21)
//getFirst(), getLast(), addFirst(), addLast() and reversed()
public class HotelCheckInQueue7 {

    public static void main(String[] args) {

        List<String> checkInQueue = new ArrayList<>();

        checkInQueue.add("Arun");
        checkInQueue.add("Priya");
        checkInQueue.add("Kumar");

        System.out.println("First Guest: " + checkInQueue.getFirst());

        System.out.println("Last Guest: " + checkInQueue.getLast());

        System.out.println("All Guests: " + checkInQueue);

        System.out.println("Reverse Queue: "
                + checkInQueue.reversed());

        checkInQueue.addFirst("Meena");

        checkInQueue.addLast("Ravi");

        System.out.println("Updated Queue: " + checkInQueue);
    }
}