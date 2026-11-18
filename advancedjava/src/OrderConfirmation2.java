public class OrderConfirmation2 {

    public static void main(String[] args) {
        String billnumber = "HTL-2026-00981";
        String orderJson = """
                {
                    "orderId": '%s',
                    "guestName": "Arun",
                    "restaurant": "Ocean View",
                    "item": "Paneer Tikka",
                    "quantity": 2,
                    "orderStatus": "CONFIRMED"
                     
                }
                """.formatted(billnumber);

        System.out.println(orderJson);
    }
}

//Triple double quotes define a text block.
//no need for repeated \n or string concatenation.
//Useful for JSON, SQL queries, and HTML templates.