import module java.base;

/**
 * FEATURE: module import declarations (JDK 25)
 *   'import module java.base;' imports every public type from every
 *   package that the module exports - one line instead of many:
 *
 *     import java.math.BigDecimal;
 *     import java.math.RoundingMode;
 *     import java.time.DayOfWeek;
 *     import java.time.LocalDate;
 *     import java.util.List;
 *     import java.util.Map;
 *     import java.util.Comparator;
 *     import java.util.TreeMap;
 *     import java.util.stream.Collectors;
 *
 *   Works with any module, e.g. 'import module java.sql;'.
 *
 * BUSINESS FLOW (Restaurant weekly menu analytics):
 *   1. Load last week's sales lines
 *   2. Find the top 3 dishes by revenue (to promote)
 *   3. Find the slowest dish (candidate to remove from the menu)
 *   4. Revenue per weekday (to plan staff shifts)
 *
 * Run: java ModuleImportMenuAnalytics.java
 */
public class ModuleImportMenuAnalytics {

    record SaleLine(LocalDate saleDate, String dishName, int quantitySold, BigDecimal unitPrice) {
        BigDecimal revenue() {
            return unitPrice.multiply(BigDecimal.valueOf(quantitySold));
        }
    }

    public static void main(String[] args) {
        List<SaleLine> lastWeekSales = loadLastWeekSales();

        System.out.println("=== Top 3 dishes by revenue (promote) ===");
        findTopDishesByRevenue(lastWeekSales, 3)
                .forEach((dishName, revenue) -> System.out.printf("%-18s $%s%n", dishName, revenue));

        System.out.println("\n=== Slowest dish (review / remove) ===");
        System.out.println(findSlowestDish(lastWeekSales));

        System.out.println("\n=== Revenue by weekday (staff planning) ===");
        calculateRevenueByWeekday(lastWeekSales)
                .forEach((weekday, revenue) -> System.out.printf("%-10s $%s%n", weekday, revenue));
    }

    static Map<String, BigDecimal> findTopDishesByRevenue(List<SaleLine> sales, int limit) {
        Map<String, BigDecimal> revenueByDish = sales.stream()
                .collect(Collectors.groupingBy(SaleLine::dishName,
                        Collectors.reducing(BigDecimal.ZERO, SaleLine::revenue, BigDecimal::add)));

        return revenueByDish.entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (first, second) -> first, LinkedHashMap::new));
    }

    static String findSlowestDish(List<SaleLine> sales) {
        Map<String, Integer> quantityByDish = sales.stream()
                .collect(Collectors.groupingBy(SaleLine::dishName, Collectors.summingInt(SaleLine::quantitySold)));

        return quantityByDish.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey() + " - only " + entry.getValue() + " sold")
                .orElse("No sales");
    }

    static Map<DayOfWeek, BigDecimal> calculateRevenueByWeekday(List<SaleLine> sales) {
        return sales.stream()
                .collect(Collectors.groupingBy(sale -> sale.saleDate().getDayOfWeek(), TreeMap::new,
                        Collectors.reducing(BigDecimal.ZERO, SaleLine::revenue, BigDecimal::add)));
    }

    static List<SaleLine> loadLastWeekSales() {
        LocalDate monday = LocalDate.of(2026, 11, 9);
        return List.of(
                new SaleLine(monday,               "Margherita Pizza", 18, new BigDecimal("12.00")),
                new SaleLine(monday,               "Caesar Salad",      6, new BigDecimal("9.50")),
                new SaleLine(monday.plusDays(2),   "Margherita Pizza", 22, new BigDecimal("12.00")),
                new SaleLine(monday.plusDays(2),   "Lobster Linguine",  4, new BigDecimal("32.00")),
                new SaleLine(monday.plusDays(4),   "Truffle Burger",   35, new BigDecimal("18.50")),
                new SaleLine(monday.plusDays(4),   "Lobster Linguine", 11, new BigDecimal("32.00")),
                new SaleLine(monday.plusDays(5),   "Truffle Burger",   48, new BigDecimal("18.50")),
                new SaleLine(monday.plusDays(5),   "Beetroot Soup",     2, new BigDecimal("7.00")),
                new SaleLine(monday.plusDays(6),   "Margherita Pizza", 30, new BigDecimal("12.00")),
                new SaleLine(monday.plusDays(6),   "Caesar Salad",     14, new BigDecimal("9.50")));
    }
}
