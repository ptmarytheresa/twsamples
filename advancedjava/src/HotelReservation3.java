public class HotelReservation3 {

    record RoomReservation(
            int reservationId,
            String guestName,
            int roomNumber,
            double bookingAmount) {

        RoomReservation {
            guestName = "mary";
        }
    }

    public static void main(String[] args) {

        RoomReservation reservation =
                new RoomReservation(101, "Arun", 205, 4500.00);

        System.out.println("Guest: " + reservation.guestName());
        System.out.println("Room: " + reservation.roomNumber());
        System.out.println("Amount: " + reservation.bookingAmount());
    }
}
