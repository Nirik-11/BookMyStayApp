import java.util.LinkedList;
import java.util.Queue;

// Mock Reservation class to represent guest intent
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Reservation[Guest: " + guestName + ", Room: " + roomType + "]";
    }
}

public class BookMyStayApp {
    // Key Concept: Using Queue to decouple intake from processing
    private Queue<Reservation> bookingRequestQueue;

    public BookMyStayApp() {
        // LinkedList implements the Queue interface in Java
        this.bookingRequestQueue = new LinkedList<>();
    }

    /**
     * Requirement: Accept booking requests and store them in arrival order.
     * No inventory mutation occurs here.
     */
    public void submitBookingRequest(String guestName, String roomType) {
        Reservation newRequest = new Reservation(guestName, roomType);

        // add() or offer() ensures the element goes to the back of the line
        bookingRequestQueue.offer(newRequest);

        System.out.println("Request queued: " + guestName + " wants a " + roomType);
    }

    /**
     * Requirement: Prepare requests for subsequent processing.
     * This method shows the FIFO order without actually allocating yet.
     */
    public void displayPendingRequests() {
        if (bookingRequestQueue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }
        System.out.println("\n--- Current Booking Queue (First-Come-First-Served) ---");
        for (Reservation res : bookingRequestQueue) {
            System.out.println(res);
        }
    }

    public static void main(String[] args) {
        BookMyStayApp bookMyStay = new BookMyStayApp();

        // Simulating simultaneous/sequential arrivals
        bookMyStay.submitBookingRequest("Alice", "Deluxe");
        bookMyStay.submitBookingRequest("Bob", "Suite");
        bookMyStay.submitBookingRequest("Charlie", "Deluxe");

        // Verify ordering
        bookMyStay.displayPendingRequests();
    }
}