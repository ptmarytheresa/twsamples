package samplejava8;

import java.util.List;

public class Streamone {
        public static void main(String[] args) {
            List<Double> orderAmounts =
                    List.of(250.0, 750.0, 1200.0, 400.0, 900.0);

            double total = orderAmounts.stream()
                    .filter(amount -> amount > 500)
                    .mapToDouble(amount -> amount)
                    .sum();

            System.out.println("Total premium orders: ₹" + total);
        }

}
