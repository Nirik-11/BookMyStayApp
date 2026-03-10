
abstract class Room {
    private String roomType;
    private int beds;
    private double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() { return roomType; }
    public int getBeds() { return beds; }
    public double getPrice() { return price; }


    public abstract void displayRoomFeatures();
}


class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 100.0);
    }

    @Override
    public void displayRoomFeatures() {
        System.out.println("Features: Compact space, perfect for solo travelers.");
    }
}

// Concrete class for Double Rooms
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 180.0);
    }

    @Override
    public void displayRoomFeatures() {
        System.out.println("Features: Spacious room with twin or queen beds.");
    }
}

// Concrete class for Suite Rooms
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 2, 350.0);
    }

    @Override
    public void displayRoomFeatures() {
        System.out.println("Features: Luxury living area, mini-bar, and premium view.");
    }
}

public class BookMyStayApp{
    public static void main(String[] args) {
        System.out.println("--- Book My Stay App v2.0: Room Initialization ---");


        Room single = new SingleRoom();
        Room doubleRm = new DoubleRoom();
        Room suite = new SuiteRoom();


        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;


        displayInfo(single, singleRoomAvailability);
        displayInfo(doubleRm, doubleRoomAvailability);
        displayInfo(suite, suiteRoomAvailability);

        System.out.println("--------------------------------------------------");
        System.out.println("System terminated successfully.");
    }

    private static void displayInfo(Room room, int count) {
        System.out.println("\nRoom Type: " + room.getRoomType());
        System.out.println("Beds     : " + room.getBeds());
        System.out.println("Price    : $" + room.getPrice());
        System.out.print("Status   : ");
        room.displayRoomFeatures();
        System.out.println("Available: " + count + " units left.");
    }
}
