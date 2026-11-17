package extra;

import java.util.List;

/**
 * FEATURE: records (JDK 16)
 *   - immutable data carriers in one line
 *   - auto-generated constructor, getters, equals, hashCode, toString
 *   - compact constructor for validation
 *   - can still have methods and static factories
 *
 * BUSINESS FLOW (Restaurant billing):
 *   1. Waiter adds order lines to a table
 *   2. System validates each line (no zero qty / negative price)
 *   3. Invoice computes subtotal, service charge and tax
 *
 * Run: java RecordGuestInvoice.java
 */
public class RecordGuestInvoice {

    record OrderLine(String dishName, int quantity, double unitPrice) {
        // Compact constructor: validation without repeating field assignments
        OrderLine {
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be positive for " + dishName);
            }
            if (unitPrice < 0) {
                throw new IllegalArgumentException("Price cannot be negative for " + dishName);
            }
        }

        double lineTotal() {
            return quantity * unitPrice;
        }
    }

    record Invoice(int tableNumber, List<OrderLine> orderLines) {
        private static final double SERVICE_CHARGE_RATE = 0.10;
        private static final double TAX_RATE = 0.05;

        Invoice {
            orderLines = List.copyOf(orderLines);   // defensive, truly immutable
        }

        double subtotal()      { return orderLines.stream().mapToDouble(OrderLine::lineTotal).sum(); }
        double serviceCharge() { return subtotal() * SERVICE_CHARGE_RATE; }
        double tax()           { return (subtotal() + serviceCharge()) * TAX_RATE; }
        double grandTotal()    { return subtotal() + serviceCharge() + tax(); }
    }

    public static void main(String[] args) {
        var invoice = new Invoice(12, List.of(
                new OrderLine("Paneer Tikka", 2, 9.50),
                new OrderLine("Butter Chicken", 1, 14.00),
                new OrderLine("Garlic Naan", 4, 2.25),
                new OrderLine("Mango Lassi", 3, 4.00)));

        printInvoice(invoice);

        // equals() is value-based: handy for detecting duplicate order lines
        var lineFromWaiterTablet = new OrderLine("Garlic Naan", 4, 2.25);
        var lineFromKitchenScreen = new OrderLine("Garlic Naan", 4, 2.25);
        System.out.println("\nSame order line on both devices? " + lineFromWaiterTablet.equals(lineFromKitchenScreen));
        System.out.println("toString(): " + lineFromWaiterTablet);

        // Validation in action
        try {
            new OrderLine("Chef Special", 0, 20.0);
        } catch (IllegalArgumentException exception) {
            System.out.println("Rejected: " + exception.getMessage());
        }
    }

    static void printInvoice(Invoice invoice) {
        System.out.println("=== Invoice - Table " + invoice.tableNumber() + " ===");
        for (OrderLine line : invoice.orderLines()) {
            System.out.printf("%-15s x%d  $%6.2f%n", line.dishName(), line.quantity(), line.lineTotal());
        }
        System.out.printf("Subtotal        $%6.2f%n", invoice.subtotal());
        System.out.printf("Service (10%%)   $%6.2f%n", invoice.serviceCharge());
        System.out.printf("Tax (5%%)        $%6.2f%n", invoice.tax());
        System.out.printf("GRAND TOTAL     $%6.2f%n", invoice.grandTotal());
    }
}
