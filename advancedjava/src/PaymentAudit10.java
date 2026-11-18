//unnamed variable - jdk22
public class PaymentAudit10 {

    record PaymentResponse(
            String transactionId,
            double amount,
            String paymentStatus,
            String transactionTimestamp) {
    }

    public static void auditPayment(PaymentResponse response) {

        if (response instanceof PaymentResponse(
                String transactionId,
                double amount,
                String paymentStatus,
                _)) {

            System.out.println("Transaction ID: " + transactionId);
            System.out.println("Amount: " + amount);
            System.out.println("Status: " + paymentStatus);
        }
    }

    public static void main(String[] args) {

        PaymentResponse response = new PaymentResponse(
                "TXN501",
                4500.00,
                "SUCCESS",
                "2026-11-16 10:30"
        );

        auditPayment(response);
    }
}

//_ represents an unnamed variable or pattern.
//It is useful when a value is required syntactically but is not needed.
//Multiple unnamed variables can be used in the same scope.
//
//try {
//processGuestBooking();
//} catch (Exception _) {
//        System.out.println("Unable to process booking");
//}