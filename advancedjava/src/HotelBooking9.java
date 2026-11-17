class Booking {

    String guestName;

    Booking(String guestName) {
        this.guestName = guestName;
    }
}

class HotelBooking extends Booking {

    HotelBooking(String guestName) {

        if (guestName == null || guestName.isBlank()) {
            throw new IllegalArgumentException(
                    "Guest name cannot be empty");
        }

        super(guestName); //?
    }

    public static void main(String[] args) {

        HotelBooking booking = new HotelBooking("Arun");

        System.out.println("Booking created for: "
                + booking.guestName);
    }
}