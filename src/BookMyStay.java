import java.util.*;

class Reservation {
    String reservationId;
    String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public boolean allocate(String type) {
        int count = inventory.getOrDefault(type, 0);
        if (count > 0) {
            inventory.put(type, count - 1);
            return true;
        }
        return false;
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

class RoomAllocationService {
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        boolean success = inventory.allocate(reservation.roomType);
        if (success) {
            System.out.println("Booking confirmed for " + reservation.reservationId);
        } else {
            System.out.println("Booking failed for " + reservation.reservationId + " (No availability)");
        }
    }
}

class ConcurrentBookingProcessor implements Runnable {
    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService
    ) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {
        while (true) {
            Reservation reservation;

            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) {
                    break;
                }
                reservation = bookingQueue.getNextRequest();
            }

            synchronized (inventory) {
                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        inventory.addRoomType("Single", 2);

        queue.addRequest(new Reservation("R1", "Single"));
        queue.addRequest(new Reservation("R2", "Single"));
        queue.addRequest(new Reservation("R3", "Single"));
        queue.addRequest(new Reservation("R4", "Single"));

        Thread t1 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));
        Thread t3 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));

        t1.start();
        t2.start();
        t3.start();
    }
}