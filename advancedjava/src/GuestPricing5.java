//switch patter jdk16
class PremiumGuest {
    public double getDiscount() {
        return 20.0;
    }
}

public class GuestPricing5 {

    public static void calculateSpecialPrice(Object guest) {
// no type casting is required
        if (guest instanceof PremiumGuest premiumGuest) {

            System.out.println(
                    "Premium discount: " + premiumGuest.getDiscount()
            );

        } else if (guest instanceof RegularGuest regularGuest) {

            System.out.println(
                    "Regular discount: " + regularGuest.getDiscount()
            );

        } else {

            System.out.println("No special discount available");
        }
    }

//    static class PremiumGuest {
//        public double getDiscount() {
//            return 20.0;
//        }
//    }

    static class RegularGuest {
        public double getDiscount() {
            return 5.0;
        }
    }

    public static void main(String[] args) {

        calculateSpecialPrice(new PremiumGuest());
        calculateSpecialPrice(new RegularGuest());
    }
}

//The variable declared in instanceof is automatically cast.
//No separate typecasting is required.