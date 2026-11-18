// record pattern (jdk21)
public class ReservationProcessing31 {

    record Guest(String guestName, String mobileNumber) {
    }

    record Reservation(
            int reservationId,
            Guest guest,
            double bookingAmount) {
    }

    public static void processReservation(Object booking) {

        if (booking instanceof Reservation(
                int reservationId,
                Guest(String guestName, String mobileNumber),
                double bookingAmount)) {

            System.out.println("Reservation ID: " + reservationId);
            System.out.println("Guest Name: " + guestName);
            System.out.println("Mobile: " + mobileNumber);
            System.out.println("Amount: " + bookingAmount);
        }
    }

    public static void main(String[] args) {

        Guest guest = new Guest("Arun", "9876543210");

        Reservation reservation =
                new Reservation(501, guest, 5500.00);

        processReservation(reservation);
    }
}

//Record patterns destructure records.
//Nested record patterns are supported.
