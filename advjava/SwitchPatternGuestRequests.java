import java.util.Arrays;
import java.util.List;

/**
 * FEATURE: pattern matching for switch (JDK 21)
 *   - 'case SomeType variable ->' : switch on TYPE, not just values
 *   - guards with 'when' for extra business conditions
 *   - 'case null' handled inside the switch
 *   - exhaustive over a sealed interface: no default needed
 *
 * BUSINESS FLOW (Hotel guest-request desk):
 *   Requests from the guest app are routed to the right department
 *   with the right priority and cost.
 *
 * Run: java SwitchPatternGuestRequests.java
 */
public class SwitchPatternGuestRequests {

    sealed interface GuestRequest
            permits HousekeepingRequest, RoomServiceOrder, LateCheckoutRequest, MaintenanceIssue {}

    record HousekeepingRequest(String roomNumber, String item) implements GuestRequest {}

    record RoomServiceOrder(String roomNumber, double orderAmount) implements GuestRequest {}

    record LateCheckoutRequest(String roomNumber, int requestedHour, boolean isLoyaltyMember) implements GuestRequest {}

    record MaintenanceIssue(String roomNumber, String problem, boolean isUrgent) implements GuestRequest {}

    public static void main(String[] args) {
        List<GuestRequest> incomingRequests = Arrays.asList(
                new HousekeepingRequest("305", "Extra towels"),
                new RoomServiceOrder("412", 18.00),
                new RoomServiceOrder("508", 145.00),
                new LateCheckoutRequest("210", 13, false),
                new LateCheckoutRequest("611", 16, true),
                new LateCheckoutRequest("118", 16, false),
                new MaintenanceIssue("702", "Water leak", true),
                new MaintenanceIssue("303", "TV remote not working", false),
                null);   // e.g. corrupted message from the app

        System.out.println("=== Request routing ===");
        for (GuestRequest request : incomingRequests) {
            System.out.println(routeRequest(request));
        }
    }

    static String routeRequest(GuestRequest request) {
        return switch (request) {
            case null ->
                    "[IGNORED]      Empty request received - logged for IT";

            case HousekeepingRequest housekeeping ->
                    "[HOUSEKEEPING] Room %s: deliver '%s' (free)"
                            .formatted(housekeeping.roomNumber(), housekeeping.item());

            // Guard: more specific case must come BEFORE the general one
            case RoomServiceOrder roomService when roomService.orderAmount() < 25 ->
                    "[KITCHEN]      Room %s: $%.2f order + $5.00 tray charge (below minimum)"
                            .formatted(roomService.roomNumber(), roomService.orderAmount());

            case RoomServiceOrder roomService ->
                    "[KITCHEN]      Room %s: $%.2f order, no tray charge"
                            .formatted(roomService.roomNumber(), roomService.orderAmount());

            case LateCheckoutRequest lateCheckout when lateCheckout.requestedHour() <= 13 ->
                    "[FRONT DESK]   Room %s: checkout %d:00 approved free"
                            .formatted(lateCheckout.roomNumber(), lateCheckout.requestedHour());

            case LateCheckoutRequest lateCheckout when lateCheckout.isLoyaltyMember() ->
                    "[FRONT DESK]   Room %s: checkout %d:00 approved free (loyalty perk)"
                            .formatted(lateCheckout.roomNumber(), lateCheckout.requestedHour());

            case LateCheckoutRequest lateCheckout ->
                    "[FRONT DESK]   Room %s: checkout %d:00 approved, charge $%d"
                            .formatted(lateCheckout.roomNumber(), lateCheckout.requestedHour(),
                                    (lateCheckout.requestedHour() - 12) * 15);

            case MaintenanceIssue issue when issue.isUrgent() ->
                    "[ENGINEERING]  URGENT Room %s: '%s' - technician in 10 min, offer room move"
                            .formatted(issue.roomNumber(), issue.problem());

            case MaintenanceIssue issue ->
                    "[ENGINEERING]  Room %s: '%s' - schedule within 2 hours"
                            .formatted(issue.roomNumber(), issue.problem());
        };
    }
}
