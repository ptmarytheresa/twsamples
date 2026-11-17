import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * FEATURE: virtual threads (JDK 21)
 *   - very cheap threads managed by the JVM, not the OS
 *   - perfect for I/O-heavy work (HTTP calls, DB, payment gateways)
 *   - keep simple blocking code, get async-level scalability
 *
 * BUSINESS FLOW (Friday-night food-delivery rush):
 *   1,000 online orders arrive at once. Each order must call a slow
 *   payment gateway (~200 ms) and then notify the kitchen (~100 ms).
 *   Compare a fixed pool of 50 platform threads vs. virtual threads.
 *
 * Run: java VirtualThreadsOrderProcessing.java
 */
public class VirtualThreadsOrderProcessing {

    record OnlineOrder(int orderNumber, String customerName, double orderAmount) {}

    record OrderResult(int orderNumber, boolean paymentApproved, String kitchenTicketId) {}

    private static final int NUMBER_OF_ORDERS = 1_000;

    public static void main(String[] args) throws Exception {
        List<OnlineOrder> rushHourOrders = createRushHourOrders();

        try (ExecutorService platformThreadPool = Executors.newFixedThreadPool(50)) {
            processAllOrders("Platform threads (pool of 50)", platformThreadPool, rushHourOrders);
        }

        // One NEW virtual thread per order - no pool sizing needed
        try (ExecutorService virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            processAllOrders("Virtual threads (1 per order)", virtualThreadExecutor, rushHourOrders);
        }

        // Starting a single virtual thread directly
        Thread receiptPrinter = Thread.ofVirtual()
                .name("receipt-printer")
                .start(() -> System.out.println("\nPrinting end-of-rush summary on " + Thread.currentThread()));
        receiptPrinter.join();
    }

    static void processAllOrders(String strategyName, ExecutorService executor,
                                 List<OnlineOrder> orders) throws Exception {
        Instant startTime = Instant.now();

        List<Future<OrderResult>> pendingResults = new ArrayList<>();
        for (OnlineOrder order : orders) {
            pendingResults.add(executor.submit(() -> processSingleOrder(order)));
        }

        int approvedOrderCount = 0;
        for (Future<OrderResult> pendingResult : pendingResults) {
            if (pendingResult.get().paymentApproved()) {
                approvedOrderCount++;
            }
        }

        long elapsedMillis = Duration.between(startTime, Instant.now()).toMillis();
        System.out.printf("%-32s %d orders processed, %d approved in %,d ms%n",
                strategyName, orders.size(), approvedOrderCount, elapsedMillis);
    }

    /** Plain, readable, blocking code - virtual threads make it scale. */
    static OrderResult processSingleOrder(OnlineOrder order) throws InterruptedException {
        boolean paymentApproved = chargePaymentGateway(order);
        String kitchenTicketId = paymentApproved ? sendToKitchenDisplay(order) : null;
        return new OrderResult(order.orderNumber(), paymentApproved, kitchenTicketId);
    }

    static boolean chargePaymentGateway(OnlineOrder order) throws InterruptedException {
        Thread.sleep(200);                       // simulated remote call
        return order.orderAmount() < 500;        // fraud rule: block very large orders
    }

    static String sendToKitchenDisplay(OnlineOrder order) throws InterruptedException {
        Thread.sleep(100);                       // simulated remote call
        return "KDS-" + order.orderNumber();
    }

    static List<OnlineOrder> createRushHourOrders() {
        List<OnlineOrder> orders = new ArrayList<>();
        for (int orderNumber = 1; orderNumber <= NUMBER_OF_ORDERS; orderNumber++) {
            double orderAmount = (orderNumber % 100 == 0) ? 750.0 : 25.0 + (orderNumber % 40);
            orders.add(new OnlineOrder(orderNumber, "Customer-" + orderNumber, orderAmount));
        }
        return orders;
    }
}
