

import java.util.*;

class Booking {
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
}

class CancellationService {
    private Map<String, Booking> bookings;
    private Map<String, Integer> inventory;
    private Stack<String> rollbackStack;

    public CancellationService(Map<String, Booking> bookings, Map<String, Integer> inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
        this.rollbackStack = new Stack<>();
    }

    public void cancelBooking(String bookingId) {
        if (!bookings.containsKey(bookingId)) {
            System.out.println("Cancellation failed: Booking ID does not exist.");
            return;
        }

        Booking booking = bookings.get(bookingId);

        if (booking.isCancelled) {
            System.out.println("Cancellation failed: Booking already cancelled.");
            return;
        }


        rollbackStack.push(booking.roomId);


        inventory.put(booking.roomType, inventory.getOrDefault(booking.roomType, 0) + 1);


        booking.isCancelled = true;

        System.out.println("Booking " + bookingId + " cancelled successfully.");
        System.out.println("Room " + booking.roomId + " released back to inventory.");
    }

    public void showRollbackHistory() {
        System.out.println("Rollback Stack (recently released rooms): " + rollbackStack);
    }

    public void showInventory() {
        System.out.println("Current Inventory: " + inventory);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);


        Map<String, Booking> bookings = new HashMap<>();
        bookings.put("B001", new Booking("B001", "Alice", "Deluxe", "D101"));
        bookings.put("B002", new Booking("B002", "Bob", "Suite", "S201"));

        CancellationService service = new CancellationService(bookings, inventory);


        System.out.println("Initial Inventory: " + inventory);


        service.cancelBooking("B001");
        service.cancelBooking("B001");
        service.cancelBooking("B003");


        service.showRollbackHistory();
        service.showInventory();
    }
}