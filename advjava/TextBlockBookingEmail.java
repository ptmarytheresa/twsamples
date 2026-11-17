/**
 * FEATURE: text blocks (JDK 15)
 *   - multi-line strings with """ ... """
 *   - no escaping of quotes, no "\n" + concatenation
 *   - combine with String.formatted(...)
 *
 * BUSINESS FLOW (Hotel booking confirmation):
 *   1. Build the confirmation e-mail sent to the guest
 *   2. Build the JSON payload pushed to the channel manager (Booking.com, Expedia...)
 *   3. Build the SQL used to block the room in inventory
 *
 * Run: java TextBlockBookingEmail.java
 */
public class TextBlockBookingEmail {

    public static void main(String[] args) {
        String bookingReference = "HTL-2026-00981";
        String guestName = "Priya Sharma";
        String roomType = "Deluxe Sea View";
        String checkInDate = "2026-12-20";
        String checkOutDate = "2026-12-24";
        double totalAmount = 806.40;

        System.out.println(buildConfirmationEmail(bookingReference, guestName, roomType,
                checkInDate, checkOutDate, totalAmount));
        System.out.println(buildChannelManagerPayload(bookingReference, roomType, checkInDate, checkOutDate));
        System.out.println(buildBlockRoomSql(bookingReference, roomType));
    }

    static String buildConfirmationEmail(String bookingReference, String guestName, String roomType,
                                         String checkInDate, String checkOutDate, double totalAmount) {
        return """
                Subject: Your stay is confirmed - %s

                Dear %s,

                Thank you for choosing Grand Palm Hotel.
                Room      : %s
                Check-in  : %s (from 2:00 PM)
                Check-out : %s (until 11:00 AM)
                Total     : $%.2f

                Reply to this e-mail for airport pickup or "early check-in" requests.

                Warm regards,
                Front Office Team
                """.formatted(bookingReference, guestName, roomType, checkInDate, checkOutDate, totalAmount);
    }

    static String buildChannelManagerPayload(String bookingReference, String roomType,
                                             String checkInDate, String checkOutDate) {
        return """
                {
                  "reference": "%s",
                  "roomType": "%s",
                  "stay": { "from": "%s", "to": "%s" },
                  "status": "CONFIRMED"
                }
                """.formatted(bookingReference, roomType, checkInDate, checkOutDate);
    }

    static String buildBlockRoomSql(String bookingReference, String roomType) {
        // NOTE: in real code use PreparedStatement parameters, not formatted()
        return """
                UPDATE room_inventory
                   SET available_count = available_count - 1,
                       last_booking_ref = '%s'
                 WHERE room_type = '%s'
                   AND available_count > 0;
                """.formatted(bookingReference, roomType);
    }
}
