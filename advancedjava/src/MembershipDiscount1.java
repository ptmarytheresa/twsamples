public class MembershipDiscount1 {

    public static double calculateDiscount(String membershipType) {

        return switch (membershipType) {
            case "GOLD" -> 20.0*5;
            case "SILVER" -> 10.0;
            case "BRONZE" -> 5.0;
            default -> 0.0;
        };
    }

    public static void main(String[] args) {

        double discount = calculateDiscount("GOLD");

        System.out.println("Discount: " + discount + "%");
    }
}