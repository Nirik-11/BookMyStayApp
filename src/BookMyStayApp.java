import java.util.HashMap;
import java.util.Map;


class RoomInventory {

    private HashMap<String, Integer> inventory;


    public RoomInventory() {
        this.inventory = new HashMap<>();
    }


    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }


    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }


    public boolean updateInventory(String roomType, int change) {
        if (inventory.containsKey(roomType)) {
            int currentCount = inventory.get(roomType);
            if (currentCount + change >= 0) {
                inventory.put(roomType, currentCount + change);
                return true;
            }
        }
        return false;
    }


    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() + " | Available: " + entry.getValue());
        }
        System.out.println("-------------------------------\n");
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        RoomInventory hotelInventory = new RoomInventory();


        hotelInventory.addRoomType("Single", 10);
        hotelInventory.addRoomType("Double", 5);
        hotelInventory.addRoomType("Suite", 2);

        System.out.println("System Initialized...");


        hotelInventory.displayInventory();


        System.out.println("Processing Booking: 1 Double Room...");
        if (hotelInventory.updateInventory("Double", -1)) {
            System.out.println("Booking Successful!");
        } else {
            System.out.println("Booking Failed: No availability.");
        }


        int currentSuites = hotelInventory.getAvailability("Suite");
        System.out.println("Direct Lookup - Suites remaining: " + currentSuites);


        hotelInventory.displayInventory();
    }
}