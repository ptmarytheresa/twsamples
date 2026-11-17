import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * FEATURE: unnamed variables & patterns  '_'  (JDK 22)
 *   Use '_' when a variable or record component is required by syntax
 *   but your business logic does not need it. Clear intent, no unused warnings.
 *     - catch (Exception _)
 *     - lambda parameters  _ -> ...
 *     - enhanced for / local variables
 *     - record patterns   DeliveryOrder(_, var total)
 *     - several types in one case:  case A _, B _ ->
 *
 * BUSINESS FLOW (Restaurant end-of-day report):
 *   1. Parse table numbers typed by staff (skip bad input)
 *   2. Count covers (guests served)
 *   3. Split revenue by channel
 *   4. Initialise a per-waiter tip map
 *
 * Run: java UnnamedVariablesDailyReport.java
 */
public class UnnamedVariablesDailyReport {

    sealed interface Order permits DineInOrder, TakeawayOrder, DeliveryOrder {}

    record DineInOrder(int tableNumber, int guestCount, double billAmount) implements Order {}

    record TakeawayOrder(String customerName, double billAmount) implements Order {}

    record DeliveryOrder(String customerName, String deliveryPartner, double billAmount) implements Order {}

    public static void main(String[] args) {
        List<Order> todaysOrders = List.of(
                new DineInOrder(4, 2, 58.00),
                new DineInOrder(9, 5, 212.50),
                new TakeawayOrder("Ravi", 24.00),
                new DeliveryOrder("Emma", "UberEats", 41.00),
                new DeliveryOrder("Noah", "DoorDash", 36.50));

        System.out.println("=== End-of-day report ===");
        System.out.println("Valid table numbers  : " + parseTableNumbers(List.of("4", "9", "A7", "", "12")));
        System.out.println("Total orders         : " + countOrders(todaysOrders));
        System.out.println("Covers (guests)      : " + countCovers(todaysOrders));
        System.out.printf ("Delivery revenue     : $%.2f%n", deliveryRevenue(todaysOrders));
        System.out.println("Order channels       : " + todaysOrders.stream().map(UnnamedVariablesDailyReport::channelOf).toList());
        System.out.println("Tip pool initialised : " + createEmptyTipPool(List.of("Anita", "Marco", "Lee")));
    }

    /** '_' in catch: we don't care about the exception details, just skip bad input. */
    static List<Integer> parseTableNumbers(List<String> rawInputs) {
        return rawInputs.stream()
                .<Integer>mapMulti((rawInput, validTables) -> {
                    try {
                        validTables.accept(Integer.parseInt(rawInput));
                    } catch (NumberFormatException _) {
                        // ignore typos like "A7" or empty strings
                    }
                })
                .toList();
    }

    /** '_' in enhanced for: we only need to count, not use the element. */
    static int countOrders(List<Order> orders) {
        int orderCount = 0;
        for (Order _ : orders) {
            orderCount++;
        }
        return orderCount;
    }

    /** Unnamed pattern: only guestCount matters, ignore table number and bill. */
    static int countCovers(List<Order> orders) {
        int totalCovers = 0;
        for (Order order : orders) {
            if (order instanceof DineInOrder(_, int guestCount, _)) {
                totalCovers += guestCount;
            }
        }
        return totalCovers;
    }

    /** Unnamed components in a switch: we only need the bill amount for delivery orders. */
    static double deliveryRevenue(List<Order> orders) {
        double revenue = 0;
        for (Order order : orders) {
            revenue += switch (order) {
                case DeliveryOrder(_, _, double billAmount) -> billAmount;
                case DineInOrder _, TakeawayOrder _ -> 0.0;     // several types, no bindings
            };
        }
        return revenue;
    }

    static String channelOf(Order order) {
        return switch (order) {
            case DineInOrder _   -> "DINE_IN";
            case TakeawayOrder _ -> "TAKEAWAY";
            case DeliveryOrder(_, String deliveryPartner, _) -> "DELIVERY/" + deliveryPartner;
        };
    }

    /** '_' as lambda parameter: every waiter starts with 0.0, key not needed in the value function. */
    static Map<String, Double> createEmptyTipPool(List<String> waiterNames) {
        return waiterNames.stream().collect(Collectors.toMap(waiterName -> waiterName, _ -> 0.0));
    }
}
