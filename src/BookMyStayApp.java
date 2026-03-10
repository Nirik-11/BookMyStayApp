import java.util.*;


class Room {
    private String type;
    private double price;
    private String amenities;

    public Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() { return type; }
    public double getPrice() { return price; }
    public String getAmenities() { return amenities; }

    @Override
    public String toString() {
        return String.format("Type: %-12s | Price: $%.2f | Amenities: %s",
                type, price, amenities);
    }
}


class Inventory {
    private Map<String, Integer> availability = new HashMap<>();

    public void addRoomType(String type, int count) {
        availability.put(type, count);
    }


    public Map<String, Integer> getAvailableRooms() {
        return Collections.unmodifiableMap(availability);
    }
}


class SearchService {
    private Inventory inventory;
    private List<Room> roomDefinitions;

    public SearchService(Inventory inventory, List<Room> roomDefinitions) {
        this.inventory = inventory;
        this.roomDefinitions = roomDefinitions;
    }

    public void searchAvailableRooms() {
        System.out.println("--- Current Available Rooms ---");
        Map<String, Integer> currentStock = inventory.getAvailableRooms();
        boolean found = false;

        for (Room room : roomDefinitions) {
            int count = currentStock.getOrDefault(room.getType(), 0);


            if (count > 0) {
                System.out.println(room + " | Stock: " + count);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms currently available.");
        }
        System.out.println("-------------------------------\n");
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        Inventory hotelInventory = new Inventory();
        hotelInventory.addRoomType("Single", 5);
        hotelInventory.addRoomType("Double", 2);
        hotelInventory.addRoomType("Suite", 0); // Out of stock

        List<Room> roomDetails = Arrays.asList(
                new Room("Single", 100.0, "Wifi, Desk"),
                new Room("Double", 150.0, "Wifi, TV, Mini-fridge"),
                new Room("Suite", 300.0, "Wifi, TV, Kitchen, Ocean View")
        );


        SearchService searchService = new SearchService(hotelInventory, roomDetails);

        System.out.println("Action: Guest initiates room search...");
        searchService.searchAvailableRooms();

        System.out.println("System Check: State remains unchanged after search.");
    }
}