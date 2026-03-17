import java.util.HashMap;
import java.util.Map;


class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}


class HotelInventory {

    private Map<String, Integer> roomInventory;

    public HotelInventory() {
        roomInventory = new HashMap<>();


        roomInventory.put("Standard", 5);
        roomInventory.put("Deluxe", 3);
        roomInventory.put("Suite", 2);
    }


    public void bookRoom(String roomType, int roomsRequested) throws InvalidBookingException {


        if (!roomInventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid Room Type: " + roomType);
        }


        if (roomsRequested <= 0) {
            throw new InvalidBookingException("Rooms requested must be greater than 0.");
        }

        int availableRooms = roomInventory.get(roomType);


        if (roomsRequested > availableRooms) {
            throw new InvalidBookingException(
                    "Not enough rooms available. Requested: " + roomsRequested +
                            ", Available: " + availableRooms);
        }


        roomInventory.put(roomType, availableRooms - roomsRequested);

        System.out.println("Booking successful!");
        System.out.println(roomsRequested + " " + roomType + " room(s) booked.");
    }


    public void displayInventory() {
        System.out.println("\nCurrent Room Availability:");
        for (String type : roomInventory.keySet()) {
            System.out.println(type + " : " + roomInventory.get(type));
        }
    }
}


public class BookMyStayApp {

    public static void main(String[] args) {

        HotelInventory inventory = new HotelInventory();

        inventory.displayInventory();

        try {

            System.out.println("\nAttempting Booking 1...");
            inventory.bookRoom("Deluxe", 2);

            System.out.println("\nAttempting Booking 2...");
            inventory.bookRoom("Suite", 3); // Invalid - exceeds availability

        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }

        try {

            System.out.println("\nAttempting Booking 3...");
            inventory.bookRoom("Premium", 1); // Invalid room type

        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }

        try {

            System.out.println("\nAttempting Booking 4...");
            inventory.bookRoom("Standard", -1); // Invalid input

        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }

        inventory.displayInventory();
    }
}