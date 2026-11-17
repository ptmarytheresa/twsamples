import java.time.DayOfWeek;

/**
 * FEATURE: switch expressions (JDK 14)
 *   - switch returns a value
 *   - arrow labels (no fall-through, no 'break')
 *   - multiple labels per case
 *   - 'yield' for multi-line blocks
 *   - compiler checks every enum constant is covered
 *
 * BUSINESS FLOW:
 *   1. Hotel: add meal-plan surcharge to a guest's room rate
 *   2. Restaurant: apply happy-hour discount depending on day and hour
 *
 * Run: java SwitchExpressionMealPricing.java
 */
public class SwitchExpressionMealPricing {

    enum MealPlan { ROOM_ONLY, BED_AND_BREAKFAST, HALF_BOARD, FULL_BOARD, ALL_INCLUSIVE }

    public static void main(String[] args) {
        double baseNightlyRate = 150.0;
        int numberOfNights = 3;

        System.out.println("=== Hotel: meal plan pricing (3 nights) ===");
        for (MealPlan mealPlan : MealPlan.values()) {
            double totalCost = (baseNightlyRate + mealPlanSurchargePerNight(mealPlan)) * numberOfNights;
            System.out.printf("%-18s $%.2f%n", mealPlan, totalCost);
        }

        System.out.println("\n=== Restaurant: $80 bill discount ===");
        double billAmount = 80.0;
        printDiscountedBill(billAmount, DayOfWeek.MONDAY, 17);
        printDiscountedBill(billAmount, DayOfWeek.MONDAY, 20);
        printDiscountedBill(billAmount, DayOfWeek.THURSDAY, 17);
        printDiscountedBill(billAmount, DayOfWeek.SATURDAY, 17);
    }

    /** No 'default' needed: the compiler verifies every MealPlan is handled. */
    static double mealPlanSurchargePerNight(MealPlan mealPlan) {
        return switch (mealPlan) {
            case ROOM_ONLY         -> 0.0;
            case BED_AND_BREAKFAST -> 25.0;
            case HALF_BOARD        -> 55.0;
            case FULL_BOARD        -> 85.0;
            case ALL_INCLUSIVE     -> 140.0;
        };
    }

    static double happyHourDiscountRate(DayOfWeek dayOfWeek, int hourOfDay) {
        boolean isHappyHour = hourOfDay >= 16 && hourOfDay < 19;

        return switch (dayOfWeek) {
            case SATURDAY, SUNDAY -> 0.0;               // busy days: no discount
            case MONDAY, TUESDAY -> {                    // slow days: push footfall
                double slowDayRate = isHappyHour ? 0.25 : 0.10;
                yield slowDayRate;
            }
            default -> isHappyHour ? 0.15 : 0.0;        // Wed - Fri
        };
    }

    static void printDiscountedBill(double billAmount, DayOfWeek dayOfWeek, int hourOfDay) {
        double discountRate = happyHourDiscountRate(dayOfWeek, hourOfDay);
        double amountToPay = billAmount * (1 - discountRate);
        System.out.printf("%-9s %02d:00 -> %2.0f%% off, pay $%.2f%n",
                dayOfWeek, hourOfDay, discountRate * 100, amountToPay);
    }
}
