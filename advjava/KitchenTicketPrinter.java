/**
 * FEATURE: compact source files & instance main methods (JDK 25)
 *   - no 'public class ...' wrapper
 *   - 'void main()' instead of 'public static void main(String[] args)'
 *   - all of java.base (List, Map, LocalTime, ...) is imported automatically
 *   - IO.println(...) instead of System.out.println(...)
 *
 *   Great for small scripts, utilities, demos and teaching.
 *
 * BUSINESS FLOW (Kitchen ticket printer script):
 *   Take the current open orders, group dishes by kitchen station
 *   (grill, wok, pastry...) and print one ticket per station.
 *
 * Run: java KitchenTicketPrinter.java
 */

record KitchenItem(int tableNumber, String dishName, int quantity, String station) {}

final List<KitchenItem> openOrderItems = List.of(
        new KitchenItem(3, "Ribeye Steak", 2, "GRILL"),
        new KitchenItem(3, "Chocolate Lava Cake", 2, "PASTRY"),
        new KitchenItem(5, "Pad Thai", 1, "WOK"),
        new KitchenItem(5, "Chicken Skewers", 3, "GRILL"),
        new KitchenItem(8, "Fried Rice", 2, "WOK"),
        new KitchenItem(8, "Tiramisu", 1, "PASTRY"));

void main() {
    Map<String, List<KitchenItem>> itemsByStation = groupByStation(openOrderItems);
    itemsByStation.forEach(this::printStationTicket);
    IO.println("Tickets printed: " + itemsByStation.size());
}

Map<String, List<KitchenItem>> groupByStation(List<KitchenItem> items) {
    return items.stream().collect(Collectors.groupingBy(KitchenItem::station, TreeMap::new, Collectors.toList()));
}

void printStationTicket(String station, List<KitchenItem> stationItems) {
    String printedAt = LocalTime.now().truncatedTo(ChronoUnit.MINUTES).toString();
    IO.println("------ " + station + " STATION  (" + printedAt + ") ------");
    for (KitchenItem item : stationItems) {
        IO.println("  Table %-2d  %dx %s".formatted(item.tableNumber(), item.quantity(), item.dishName()));
    }
    IO.println("");
}
