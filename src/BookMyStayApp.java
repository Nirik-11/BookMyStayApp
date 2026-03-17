

import java.util.*;
import java.util.concurrent.*;

class BookingRequest {
    String bookingId;
    String guestName;
    String roomType;

    BookingRequest(String bookingId, String guestName, String roomType) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class ConcurrentBookingProcessor {
    private Map<String, Integer> inventory;
    private Queue<BookingRequest> bookingQueue;

    public ConcurrentBookingProcessor(Map<String, Integer> inventory) {
        this.inventory = inventory;
        this.bookingQueue = new LinkedList<>();
    }

    public synchronized void addRequest(BookingRequest request) {
        bookingQueue.offer(request);
        System.out.println("Request added: " + request.bookingId + " by " + request.guestName);
    }

    public synchronized void processRequest() {
        BookingRequest request = bookingQueue.poll();
        if (request == null) return;

        int available = inventory.getOrDefault(request.roomType, 0);
        if (available > 0) {
            inventory.put(request.roomType, available - 1);
            System.out.println("Booking confirmed: " + request.bookingId +
                    " for " + request.guestName +
                    " [Room Type: " + request.roomType + "]");
        } else {
            System.out.println("Booking failed (no inventory): " + request.bookingId +
                    " for " + request.guestName);
        }
    }

    public void showInventory() {
        System.out.println("Final Inventory: " + inventory);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) throws InterruptedException {

        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);

        ConcurrentBookingProcessor processor = new ConcurrentBookingProcessor(inventory);


        ExecutorService executor = Executors.newFixedThreadPool(3);

        Runnable guest1 = () -> {
            processor.addRequest(new BookingRequest("B001", "Alice", "Deluxe"));
            processor.processRequest();
        };

        Runnable guest2 = () -> {
            processor.addRequest(new BookingRequest("B002", "Bob", "Deluxe"));
            processor.processRequest();
        };

        Runnable guest3 = () -> {
            processor.addRequest(new BookingRequest("B003", "Charlie", "Suite"));
            processor.processRequest();
        };

        Runnable guest4 = () -> {
            processor.addRequest(new BookingRequest("B004", "Diana", "Suite"));
            processor.processRequest();
        };


        executor.execute(guest1);
        executor.execute(guest2);
        executor.execute(guest3);
        executor.execute(guest4);

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);


        processor.showInventory();
    }
}