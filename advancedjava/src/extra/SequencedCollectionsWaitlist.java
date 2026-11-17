import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedCollection;
import java.util.SequencedMap;
import java.util.SequencedSet;

/**
 * FEATURE: sequenced collections (JDK 21)
 *   New common methods for any ordered collection:
 *     getFirst(), getLast(), addFirst(), addLast(),
 *     removeFirst(), removeLast(), reversed()
 *   SequencedMap adds: firstEntry(), lastEntry(), pollFirstEntry(), putFirst(), ...
 *
 *   Before JDK 21: list.get(0), list.get(list.size() - 1),
 *   set.iterator().next(), and no easy "last" for LinkedHashSet.
 *
 * BUSINESS FLOW (Restaurant waitlist on a busy Saturday):
 *   1. Walk-in parties join the waitlist (first come, first served)
 *   2. A VIP / reservation holder is put at the front
 *   3. Host seats the next party when a table is free
 *   4. Manager views the most recent arrivals first
 *   5. Order history: see first and latest order of the evening
 *
 * Run: java SequencedCollectionsWaitlist.java
 */
public class SequencedCollectionsWaitlist {

    record WaitingParty(String partyName, int partySize) {}

    public static void main(String[] args) {
        manageWaitlist();
        trackCheckedInGuests();
        reviewOrderHistory();
    }

    static void manageWaitlist() {
        SequencedCollection<WaitingParty> waitlist = new ArrayList<>(List.of(
                new WaitingParty("Johnson", 4),
                new WaitingParty("Patel", 2),
                new WaitingParty("Garcia", 6)));

        waitlist.addLast(new WaitingParty("Chen", 3));            // normal walk-in
        waitlist.addFirst(new WaitingParty("VIP - Mr. Rossi", 2)); // VIP jumps the queue

        System.out.println("=== Waitlist ===");
        System.out.println("Next to be seated : " + waitlist.getFirst().partyName());
        System.out.println("Last in line      : " + waitlist.getLast().partyName());

        WaitingParty seatedParty = waitlist.removeFirst();         // table 5 is free
        System.out.println("Seated now        : " + seatedParty.partyName() + " (" + seatedParty.partySize() + " pax)");

        System.out.println("Manager view (newest first):");
        for (WaitingParty party : waitlist.reversed()) {           // a VIEW, no copy
            System.out.println("  - " + party.partyName());
        }
    }

    static void trackCheckedInGuests() {
        // LinkedHashSet: no duplicates + keeps arrival order + now has getFirst/getLast
        SequencedSet<String> checkedInRooms = new LinkedHashSet<>();
        checkedInRooms.add("Room 210");
        checkedInRooms.add("Room 115");
        checkedInRooms.add("Room 210");   // duplicate scan at kiosk - ignored
        checkedInRooms.add("Room 402");

        System.out.println("\n=== Hotel check-ins today ===");
        System.out.println("First check-in  : " + checkedInRooms.getFirst());
        System.out.println("Latest check-in : " + checkedInRooms.getLast());
        System.out.println("All (in order)  : " + checkedInRooms);
    }

    static void reviewOrderHistory() {
        SequencedMap<String, Double> ordersByTicketId = new LinkedHashMap<>();
        ordersByTicketId.put("T-1001", 42.50);
        ordersByTicketId.put("T-1002", 18.00);
        ordersByTicketId.put("T-1003", 96.75);
        ordersByTicketId.putFirst("T-0999", 12.00);  // late-synced order from offline POS

        System.out.println("\n=== Order history ===");
        System.out.println("First order of the evening : " + ordersByTicketId.firstEntry());
        System.out.println("Latest order               : " + ordersByTicketId.lastEntry());

        var closedOrder = ordersByTicketId.pollFirstEntry();          // send to accounting
        System.out.println("Sent to accounting         : " + closedOrder);
        System.out.println("Still open (newest first)  : " + ordersByTicketId.reversed());
    }
}
