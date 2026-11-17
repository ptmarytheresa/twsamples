import java.util.List;

/**
 * FEATURE: record patterns (JDK 21)
 *   - deconstruct a record into its components directly in a pattern
 *   - works with NESTED records
 *   - works in 'instanceof' and in 'switch'
 *   - 'var' can be used for component types
 *
 * BUSINESS FLOW (Restaurant order charges):
 *   Every order is Dine-in, Takeaway or Delivery. Delivery fee depends
 *   on distance and the city zone, which are nested inside the address.
 *
 * Run: java RecordPatternsDeliveryFee.java
 */
public class RecordPatternsDeliveryFee {

    record Address(String street, String cityZone, double distanceKm) {}

    sealed interface RestaurantOrder permits DineInOrder, TakeawayOrder, DeliveryOrder {}

    record DineInOrder(int tableNumber, double foodTotal) implements RestaurantOrder {}

    record TakeawayOrder(String customerName, double foodTotal) implements RestaurantOrder {}

    record DeliveryOrder(String customerName, Address deliveryAddress, double foodTotal) implements RestaurantOrder {}

    public static void main(String[] args) {
        List<RestaurantOrder> todaysOrders = List.of(
                new DineInOrder(7, 64.00),
                new TakeawayOrder("Ravi", 22.50),
                new DeliveryOrder("Emma", new Address("12 Oak St", "CENTRAL", 2.5), 38.00),
                new DeliveryOrder("Noah", new Address("88 Hill Rd", "SUBURB", 9.0), 41.00),
                new DeliveryOrder("Mia", new Address("5 Bay Ave", "CENTRAL", 1.0), 75.00));

        System.out.println("=== Order charges ===");
        for (RestaurantOrder order : todaysOrders) {
            System.out.println(describeCharges(order));
        }

        System.out.println("\n=== Dispatch alerts ===");
        for (RestaurantOrder order : todaysOrders) {
            printLongDistanceAlert(order);
        }
    }

    /** Record patterns in switch: pull out exactly the fields each rule needs. */
    static String describeCharges(RestaurantOrder order) {
        return switch (order) {
            case DineInOrder(int tableNumber, double foodTotal) ->
                    "Table %d: food $%.2f + 10%% service = $%.2f"
                            .formatted(tableNumber, foodTotal, foodTotal * 1.10);

            case TakeawayOrder(String customerName, double foodTotal) ->
                    "Takeaway %s: $%.2f + $0.50 packaging = $%.2f"
                            .formatted(customerName, foodTotal, foodTotal + 0.50);

            // Nested deconstruction: DeliveryOrder -> Address -> cityZone / distanceKm
            case DeliveryOrder(var customerName, Address(var street, var cityZone, var distanceKm), var foodTotal) -> {
                double deliveryFee = calculateDeliveryFee(cityZone, distanceKm, foodTotal);
                yield "Delivery %s (%s, %s): $%.2f + delivery $%.2f = $%.2f"
                        .formatted(customerName, street, cityZone, foodTotal, deliveryFee, foodTotal + deliveryFee);
            }
        };
    }

    /** Record pattern in instanceof: no getters, no casts. */
    static void printLongDistanceAlert(RestaurantOrder order) {
        if (order instanceof DeliveryOrder(String customerName, Address(String street, String zone, double km), double foodTotal)
                && km > 5) {
            System.out.printf("Assign bike rider: %s at %s is %.1f km away (%s)%n", customerName, street, km, zone);
        }
    }

    static double calculateDeliveryFee(String cityZone, double distanceKm, double foodTotal) {
        if (foodTotal >= 70) {
            return 0.0;                                    // free delivery on big orders
        }
        double zoneBaseFee = cityZone.equals("CENTRAL") ? 2.00 : 4.00;
        return zoneBaseFee + distanceKm * 0.80;
    }
}
