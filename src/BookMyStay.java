import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class BookingValidator {
    private static final Set<String> validRoomTypes = new HashSet<>(Arrays.asList("Deluxe", "Suite", "Standard"));

    public static void validate(String roomType) throws InvalidBookingException {
        if (!validRoomTypes.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type selected");
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter guest name:");
        String guestName = sc.nextLine();

        System.out.println("Enter room type:");
        String roomType = sc.nextLine();

        try {
            BookingValidator.validate(roomType);
            System.out.println("Booking succeeded");
        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        sc.close();
    }
}