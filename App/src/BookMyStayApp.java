import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * ===============================================
 * CLASS - CancellationService
 * ===============================================
 * Use Case 10: Booking Cancellation & Inventory Rollback
 * * Description:
 * This class is responsible for handling
 * booking cancellations.
 * * It ensures that:
 * - Cancelled room IDs are tracked
 * - Inventory is restored correctly
 * - Invalid cancellations are prevented
 * * A stack is used to model rollback behavior.
 * * @version 10.0
 */
class CancellationService {

    /** Stack that stores recently released room IDs. */
    private Stack<String> releasedRoomIds;

    /** Maps reservation ID to room type. */
    private Map<String, String> reservationRoomTypeMap;

    /** Initializes cancellation tracking structures. */
    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    /**
     * Registers a confirmed booking.
     * * This method simulates storing confirmation
     * data that will later be required for cancellation.
     * * @param reservationId confirmed reservation ID
     * @param roomType      allocated room type
     */
    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    /**
     * Cancels a confirmed booking and
     * restores inventory safely.
     * * @param reservationId reservation to cancel
     * @param inventory     centralized room inventory
     */
    public void cancelBooking(String reservationId, RoomInventory inventory) {
        if (reservationRoomTypeMap.containsKey(reservationId)) {
            String roomType = reservationRoomTypeMap.get(reservationId);

            // Push to stack for rollback history
            releasedRoomIds.push(reservationId);

            // Remove from active reservations
            reservationRoomTypeMap.remove(reservationId);

            // Restore inventory count
            Map<String, Integer> availability = inventory.getRoomAvailability();
            int currentCount = availability.getOrDefault(roomType, 0);
            inventory.updateAvailability(roomType, currentCount + 1);

            System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
        } else {
            System.out.println("Cancellation failed: Reservation ID not found.");
        }
    }

    /**
     * Displays recently cancelled reservations.
     * * This method helps visualize rollback order.
     */
    public void showRollbackHistory() {
        System.out.println("\nRollback History (Most Recent First):");

        // Iterate backwards over the stack to display LIFO without removing elements
        for (int i = releasedRoomIds.size() - 1; i >= 0; i--) {
            System.out.println("Released Reservation ID: " + releasedRoomIds.get(i));
        }
    }
}

/**
 * ===============================================
 * MAIN CLASS - UseCase10BookingCancellation
 * ===============================================
 * Use Case 10: Booking Cancellation & Inventory Rollback
 * * Description:
 * This class demonstrates how confirmed
 * bookings can be cancelled safely.
 * * Inventory is restored and rollback
 * history is maintained.
 * * @version 10.0
 */
public class UseCase10BookingCancellation {

    /**
     * Application entry point.
     * * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Booking Cancellation");

        // 1. Initialize required components
        RoomInventory inventory = new RoomInventory();
        CancellationService cancellationService = new CancellationService();

        // 2. Simulate registering a confirmed booking
        String reservationIdToCancel = "Single-1";
        String roomType = "Single";
        cancellationService.registerBooking(reservationIdToCancel, roomType);

        // 3. Perform cancellation
        cancellationService.cancelBooking(reservationIdToCancel, inventory);

        // 4. Show the rollback history (Stack behavior)
        cancellationService.showRollbackHistory();

        // 5. Display updated inventory to confirm restoration
        System.out.println("\nUpdated Single Room Availability: " + inventory.getRoomAvailability().get("Single"));
    }
}

/**
 * ===============================================
 * DEPENDENCY CLASS (From earlier Use Cases)
 * Included here so Use Case 10 compiles standalone
 * ===============================================
 */
class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        // Note: Default starting inventory for Single is 5 based on earlier iterations
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

