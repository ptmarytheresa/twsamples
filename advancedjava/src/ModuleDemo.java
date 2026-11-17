import module java.base;

public class ModuleDemo {

    public static void main(String[] args) {

        List<String> foodItems = List.of(
                "Pizza",
                "Burger",
                "Pasta"
        );

        LocalDate orderDate = LocalDate.now();

        System.out.println("Order Date: " + orderDate);
        System.out.println("Food Items: " + foodItems);
    }
}
