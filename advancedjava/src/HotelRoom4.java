//sealed - jdk17
public class HotelRoom4 {

    sealed interface RoomCategory
            permits StandardRoom, DeluxeRoom, SuiteRoom {
       default void standard()
        {
            System.out.println("1200 - PerNight");
        }
    }

    static final class StandardRoom implements RoomCategory {
        void showPrice()
        {
            standard();
        }
    }

    static final class DeluxeRoom implements RoomCategory {
    }

    static final class SuiteRoom implements RoomCategory {
    }


    public static void main(String[] args) {

        RoomCategory room = new DeluxeRoom();

        System.out.println("Room category: " + room.getClass().getSimpleName());
    }
}



//sealed restricts which classes can implement or extend a type.
//Permitted subclasses must follow Java's sealed-class rules.
//Useful for payment types, booking types, room categories and order states.