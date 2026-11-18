package samplejava8;

import java.util.Optional;

public class RoomService {

    public static Optional<String> findGuestRoom(int guestId) {

        if (guestId == 101) {
            return Optional.of("Room 205");
        }

        return Optional.empty();
    }

    public static void main(String[] args) {

        String room = findGuestRoom(103)
                .orElse("No active booking found");

        System.out.println(room);
    }
}