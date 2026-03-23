import java.util.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private int nights;
    private double cost;

    public Reservation(String reservationId, String guestName, String roomType, int nights, double cost) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
        this.cost = cost;
    }

    public String getReservationId() {
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

    public double getCost() {
        return cost;
    }

    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Nights: " + nights +
                ", Cost: $" + cost;
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

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }
}

class BookingReportService {

    public void displayAllBookings(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("=== Booking History ===");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    public void generateSummaryReport(List<Reservation> reservations) {
        int totalBookings = reservations.size();
        double totalRevenue = 0.0;

        for (Reservation r : reservations) {
            totalRevenue += r.getCost();
        }

        System.out.println("\n=== Summary Report ===");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: $" + totalRevenue);
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        history.addReservation(new Reservation("RES101", "Vartika", "Deluxe", 2, 200.0));
        history.addReservation(new Reservation("RES102", "Soham", "Suite", 3, 450.0));
        history.addReservation(new Reservation("RES103", "Aniruddha", "Standard", 1, 100.0));

        List<Reservation> storedReservations = history.getAllReservations();

        reportService.displayAllBookings(storedReservations);
        reportService.generateSummaryReport(storedReservations);
    }
}