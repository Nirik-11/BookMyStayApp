import java.util.ArrayList;
import java.util.List;


class Reservation {
    private int reservationId;
    private String guestName;
    private String roomType;
    private int nights;

    public Reservation(int reservationId, String guestName, String roomType, int nights) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType +
                ", Nights: " + nights;
    }
}



class BookingHistory {

    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }


    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }


    public List<Reservation> getReservations() {
        return reservations;
    }
}



class BookingReportService {


    public void displayAllBookings(List<Reservation> reservations) {

        System.out.println("\n---- Booking History ----");

        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }


    public void generateSummaryReport(List<Reservation> reservations) {

        System.out.println("\n---- Booking Summary Report ----");

        int totalBookings = reservations.size();
        int totalNights = 0;

        for (Reservation r : reservations) {
            totalNights += r.getNights();
        }

        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Nights Booked: " + totalNights);
    }
}



public class BookMyStayApp {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();


        Reservation r1 = new Reservation(101, "Arun", "Deluxe", 2);
        Reservation r2 = new Reservation(102, "Priya", "Suite", 3);
        Reservation r3 = new Reservation(103, "Rahul", "Standard", 1);


        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);


        reportService.displayAllBookings(history.getReservations());

        reportService.generateSummaryReport(history.getReservations());
    }
}
