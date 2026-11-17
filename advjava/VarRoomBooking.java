import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * FEATURE: var — local variable type inference (JDK 10, lambda params JDK 11)
 *
 * BUSINESS FLOW (Hotel front desk):
 *   1. Load today's rooms
 *   2. Group the AVAILABLE rooms by room type
 *   3. Guest picks a DELUXE room for 4 nights
 *   4. Calculate total stay cost including city tax
 *
 * Run: java VarRoomBooking.java
 */
public class VarRoomBooking {

    record Room(String roomNumber, String roomType, double nightlyRate, boolean available) {}

    private static final double CITY_TAX_RATE = 0.12;

    public static void main(String[] args) {
        // Without var: List<Room> rooms = List.of(...)
        var rooms = List.of(
                new Room("101", "STANDARD", 120.0, true),
                new Room("102", "STANDARD", 120.0, false),
                new Room("201", "DELUXE", 180.0, true),
                new Room("202", "DELUXE", 180.0, true),
                new Room("301", "SUITE", 350.0, true));

        // Without var: Map<String, List<Room>> availableRoomsByType = ...
        var availableRoomsByType = groupAvailableRoomsByType(rooms);

        System.out.println("=== Availability ===");
        // var in lambda parameters (JDK 11)
        availableRoomsByType.forEach((var roomType, var roomList) ->
                System.out.println(roomType + " -> " + roomList.size() + " room(s) available"));

        var checkInDate = LocalDate.of(2026, 12, 20);
        var checkOutDate = LocalDate.of(2026, 12, 24);
        var selectedRoom = availableRoomsByType.get("DELUXE").get(0);

        var totalStayCost = calculateStayCost(selectedRoom, checkInDate, checkOutDate);

        System.out.println("\n=== Booking Confirmed ===");
        System.out.printf("Room %s (%s) from %s to %s%n",
                selectedRoom.roomNumber(), selectedRoom.roomType(), checkInDate, checkOutDate);
        System.out.printf("Total incl. %.0f%% city tax: $%.2f%n", CITY_TAX_RATE * 100, totalStayCost);
    }

    static Map<String, List<Room>> groupAvailableRoomsByType(List<Room> rooms) {
        var roomsByType = new TreeMap<String, List<Room>>();
        for (var room : rooms) {
            if (room.available()) {
                roomsByType.computeIfAbsent(room.roomType(), key -> new ArrayList<>()).add(room);
            }
        }
        return roomsByType;
    }

    static double calculateStayCost(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        var numberOfNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        var subtotal = room.nightlyRate() * numberOfNights;
        var cityTax = subtotal * CITY_TAX_RATE;
        return subtotal + cityTax;
    }
}
