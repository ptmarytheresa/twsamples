public class OrderConfirmation2 {

    public static void main(String[] args) {

        String orderJson = """
                {
                    "orderId": 501,
                    "guestName": "Arun",
                    "restaurant": "Ocean View",
                    "item": "Paneer Tikka",
                    "quantity": 2,
                    "orderStatus": "CONFIRMED"
                }
                """;

        System.out.println(orderJson);
    }
}