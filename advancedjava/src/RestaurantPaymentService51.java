//Pattern Matching for Switch (JDK 21)
public class RestaurantPaymentService51 {

    interface PaymentMethod {
    }

    record CreditCardPayment(String cardNumber)
            implements PaymentMethod {
    }

    record UpiPayment(String upiId)
            implements PaymentMethod {
    }

    record CashPayment(double cashReceived)
            implements PaymentMethod {
    }

    public static void processPayment(PaymentMethod paymentMethod) {

        switch (paymentMethod) {

            case CreditCardPayment card ->
                    System.out.println("Processing card payment: "
                            + card.cardNumber());

            case UpiPayment upi ->
                    System.out.println("Processing UPI payment: "
                            + upi.upiId());

            case CashPayment cash ->
                    System.out.println("Cash received: "
                            + cash.cashReceived());
            default -> throw new IllegalStateException("Unexpected value: " + paymentMethod);
        }
    }

    public static void main(String[] args) {

        processPayment(new CreditCardPayment("XXXX-1234"));
        processPayment(new UpiPayment("arun@upi"));
        processPayment(new CashPayment(1000));
    }
}

//Switch can match object types.
//It can extract variables from matched types.
//When all permitted types of a sealed hierarchy are covered, the compiler can verify exhaustiveness.