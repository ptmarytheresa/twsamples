package extra;

import java.util.List;

/**
 * FEATURE: sealed classes / interfaces (JDK 17)
 *   - 'sealed ... permits' restricts WHO can implement a type
 *   - the business domain becomes a closed, known set
 *   - subclasses must be final, sealed or non-sealed
 *
 * BUSINESS FLOW (Hotel checkout):
 *   The hotel accepts exactly four payment methods. Nobody can add a
 *   fifth one (e.g. "Crypto") without changing this file, so finance
 *   rules (fees, settlement days) are always complete.
 *
 * Run: java SealedPaymentMethods.java
 */
public class SealedPaymentMethods {

    sealed interface PaymentMethod
            permits CreditCard, CorporateAccount, LoyaltyPoints, Cash {

        double processingFee(double billAmount);

        int settlementDays();
    }

    record CreditCard(String cardNetwork, String lastFourDigits) implements PaymentMethod {
        public double processingFee(double billAmount) { return billAmount * 0.025; }
        public int settlementDays() { return 2; }
    }

    record CorporateAccount(String companyName, String accountCode) implements PaymentMethod {
        public double processingFee(double billAmount) { return 0.0; }     // negotiated contract
        public int settlementDays() { return 30; }                         // invoiced monthly
    }

    record LoyaltyPoints(String memberId, int pointsUsed) implements PaymentMethod {
        public double processingFee(double billAmount) { return 0.0; }
        public int settlementDays() { return 0; }
    }

    // A sealed hierarchy can also contain normal final classes
    static final class Cash implements PaymentMethod {
        public double processingFee(double billAmount) { return 0.0; }
        public int settlementDays() { return 0; }
        @Override public String toString() { return "Cash"; }
    }

//     record Crypto(String wallet) implements PaymentMethod {
//
//    }

    public static void main(String[] args) {
        double roomBill = 840.00;

        List<PaymentMethod> paymentOptions = List.of(
                new CreditCard("VISA", "4242"),
                new CorporateAccount("Acme Corp", "ACME-001"),
                new LoyaltyPoints("GOLD-7781", 42_000),
                new Cash());

        System.out.println("=== Checkout options for $" + roomBill + " bill ===");
        for (PaymentMethod paymentMethod : paymentOptions) {
            settleBill(paymentMethod, roomBill);
        }
    }

    static void settleBill(PaymentMethod paymentMethod, double billAmount) {
        double fee = paymentMethod.processingFee(billAmount);
        double netRevenue = billAmount - fee;
        System.out.printf("%-62s fee $%5.2f | hotel receives $%.2f in %d day(s)%n",
                paymentMethod, fee, netRevenue, paymentMethod.settlementDays());
    }
}
