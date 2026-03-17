

import java.io.*;
import java.util.*;


class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    String bookingId;
    String guestName;
    String roomType;
    String roomId;
    boolean isCancelled;

    Booking(String bookingId, String guestName, String roomType, String roomId) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", guestName='" + guestName + '\'' +
                ", roomType='" + roomType + '\'' +
                ", roomId='" + roomId + '\'' +
                ", isCancelled=" + isCancelled +
                '}';
    }
}


class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;
    Map<String, Integer> inventory;
    Map<String, Booking> bookings;

    SystemState(Map<String, Integer> inventory, Map<String, Booking> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "system_state.ser";

    public static void saveState(SystemState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    public static SystemState loadState() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No persisted state found. Starting fresh.");
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            SystemState state = (SystemState) ois.readObject();
            System.out.println("System state loaded successfully.");
            return state;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading system state: " + e.getMessage());
            return null;
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        SystemState recoveredState = PersistenceService.loadState();

        Map<String, Integer> inventory;
        Map<String, Booking> bookings;

        if (recoveredState != null) {
            inventory = recoveredState.inventory;
            bookings = recoveredState.bookings;
        } else {

            inventory = new HashMap<>();
            inventory.put("Deluxe", 2);
            inventory.put("Suite", 1);

            bookings = new HashMap<>();
            bookings.put("B001", new Booking("B001", "Alice", "Deluxe", "D101"));
            bookings.put("B002", new Booking("B002", "Bob", "Suite", "S201"));
        }


        System.out.println("Current Inventory: " + inventory);
        System.out.println("Current Bookings: " + bookings.values());


        Booking newBooking = new Booking("B003", "Charlie", "Deluxe", "D102");
        bookings.put(newBooking.bookingId, newBooking);
        inventory.put("Deluxe", inventory.get("Deluxe") - 1);
        System.out.println("New booking added: " + newBooking);


        SystemState currentState = new SystemState(inventory, bookings);
        PersistenceService.saveState(currentState);

        System.out.println("Shutdown complete. Restart the program to see recovery in action.");
    }
}