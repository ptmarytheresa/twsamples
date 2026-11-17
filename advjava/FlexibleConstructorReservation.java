/**
 * FEATURE: flexible constructor bodies (JDK 25)
 *   Statements are now allowed BEFORE super(...) or this(...):
 *     - validate arguments BEFORE doing expensive parent setup
 *     - compute / normalise values to pass to the parent
 *     - assign this class's own fields before the parent constructor runs
 *   Still not allowed before super(): reading 'this' fields or calling instance methods.
 *
 * BUSINESS FLOW (Restaurant reservations):
 *   1. Normal table booking (base class), also creatable from a phone note "Name;4"
 *   2. Group-event booking (8+ guests) needs validation and a deposit
 *      BEFORE the base reservation is registered
 *
 * Run: java FlexibleConstructorReservation.java
 */
public class FlexibleConstructorReservation {

    static class TableReservation {
        private static int nextReservationId = 1;

        protected final int reservationId;
        protected final String guestName;
        protected final int partySize;

        TableReservation(String guestName, int partySize) {
            this.reservationId = nextReservationId++;    // "expensive" side effect: consumes an ID
            this.guestName = guestName;
            this.partySize = partySize;
            System.out.println("Registered -> " + summary()); // calls an overridable method
        }

        /** Secondary constructor: parse BEFORE delegating with this(...). */
        TableReservation(String phoneBookingNote) {
            String[] noteParts = phoneBookingNote.split(";");
            if (noteParts.length != 2) {
                throw new IllegalArgumentException("Expected 'Name;PartySize' but got: " + phoneBookingNote);
            }
            String parsedName = noteParts[0].trim();
            int parsedPartySize = Integer.parseInt(noteParts[1].trim());
            this(parsedName, parsedPartySize);           // ✅ JDK 25: not required to be first
        }

        String summary() {
            return "#%d %s, %d guests".formatted(reservationId, guestName, partySize);
        }
    }

    static class GroupEventReservation extends TableReservation {
        private static final int MINIMUM_GROUP_SIZE = 8;

        private final String eventType;
        private final double depositAmount;

        GroupEventReservation(String guestName, int partySize, String eventType) {
            // ---- PROLOGUE: runs BEFORE the parent constructor ----
            if (partySize < MINIMUM_GROUP_SIZE) {
                // Fails fast: no reservation ID is wasted in the parent
                throw new IllegalArgumentException(
                        "Group events need at least %d guests, got %d".formatted(MINIMUM_GROUP_SIZE, partySize));
            }
            String normalisedGuestName = guestName.trim().toUpperCase();
            this.eventType = eventType;
            this.depositAmount = calculateDeposit(partySize, eventType);

            super(normalisedGuestName, partySize);       // ✅ JDK 25: not required to be first

            // ---- EPILOGUE: normal code after super ----
            System.out.printf("  Deposit request sent to %s: $%.2f%n", this.guestName, depositAmount);
        }

        private static double calculateDeposit(int partySize, String eventType) {
            double perGuestDeposit = eventType.equals("WEDDING") ? 40.0 : 20.0;
            return partySize * perGuestDeposit;
        }

        @Override
        String summary() {
            // Because fields were assigned BEFORE super(), the parent's call to summary()
            // sees real values here. Before JDK 25 it would print "null" and "$0.00".
            return super.summary() + " | %s, deposit $%.2f".formatted(eventType, depositAmount);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Reservations ===");
        new TableReservation("Alice Wong", 2);
        new TableReservation("Bob Singh ; 4");
        new GroupEventReservation("  carla diaz ", 12, "BIRTHDAY");
        new GroupEventReservation("david & eva", 60, "WEDDING");

        try {
            new GroupEventReservation("Small Party", 5, "BIRTHDAY");
        } catch (IllegalArgumentException exception) {
            System.out.println("Rejected  -> " + exception.getMessage());
        }

        // Shows no reservation ID was consumed by the rejected booking
        new TableReservation("Frank Ito", 3);
    }
}
