import java.util.*;

class BookingRequest {
    String customerName;
    String roomType;

    BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

public class BookMyStayApp {


    private Queue<BookingRequest> requestQueue = new LinkedList<>();


    private Map<String, Integer> inventory = new HashMap<>();


    private Map<String, Set<String>> allocatedRooms = new HashMap<>();


    private Set<String> usedRoomIds = new HashSet<>();

    private int roomCounter = 1;

    public BookMyStayApp() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);

        allocatedRooms.put("Standard", new HashSet<>());
        allocatedRooms.put("Deluxe", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());
    }


    public void addBookingRequest(String customerName, String roomType) {
        requestQueue.offer(new BookingRequest(customerName, roomType));
        System.out.println("Booking request added: " + customerName + " -> " + roomType);
    }


    private String generateRoomId(String roomType) {
        String roomId;
        do {
            roomId = roomType.substring(0, 2).toUpperCase() + roomCounter++;
        } while (usedRoomIds.contains(roomId));

        usedRoomIds.add(roomId);
        return roomId;
    }


    public void processBookings() {
        while (!requestQueue.isEmpty()) {

            BookingRequest request = requestQueue.poll();
            String roomType = request.roomType;

            System.out.println("\nProcessing booking for: " + request.customerName);

            if (inventory.getOrDefault(roomType, 0) > 0) {

                String roomId = generateRoomId(roomType);

                allocatedRooms.get(roomType).add(roomId);

                inventory.put(roomType, inventory.get(roomType) - 1);

                System.out.println("Reservation Confirmed!");
                System.out.println("Customer: " + request.customerName);
                System.out.println("Room Type: " + roomType);
                System.out.println("Room ID: " + roomId);

            } else {
                System.out.println("Sorry! No " + roomType + " rooms available.");
            }
        }
    }


    public void displayAllocatedRooms() {
        System.out.println("\nAllocated Rooms:");
        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + " -> " + allocatedRooms.get(type));
        }
    }

    public static void main(String[] args) {

        BookMyStayApp service = new BookMyStayApp();

        service.addBookingRequest("Alice", "Standard");
        service.addBookingRequest("Bob", "Deluxe");
        service.addBookingRequest("Charlie", "Standard");
        service.addBookingRequest("David", "Suite");
        service.addBookingRequest("Eva", "Suite");

        service.processBookings();

        service.displayAllocatedRooms();
    }
}