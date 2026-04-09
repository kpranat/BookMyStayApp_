import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ===============================================
 * CLASS - AddOnService
 * ===============================================
 * Use Case 7: Add-On Service Selection
 * * Description:
 * This class represents an optional service
 * that can be added to a confirmed reservation.
 * * Examples:
 * - Breakfast
 * - Spa
 * - Airport Pickup
 * * @version 7.0
 */
class AddOnService {

    /**
     * Name of the service.
     */
    private String serviceName;

    /**
     * Cost of the service.
     */
    private double cost;

    /**
     * Creates a new add-on service.
     * * @param serviceName name of the service
     * @param cost        cost of the service
     */
    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    /**
     * @return service name
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @return service cost
     */
    public double getCost() {
        return cost;
    }
}

/**
 * ===============================================
 * CLASS - AddOnServiceManager
 * ===============================================
 * Use Case 7: Add-On Service Selection
 * * Description:
 * This class manages optional services
 * associated with confirmed reservations.
 * * It supports attaching multiple services
 * to a single reservation.
 * * @version 7.0
 */
class AddOnServiceManager {

    /**
     * Maps reservation ID to selected services.
     * * Key   -> Reservation ID
     * Value -> List of selected services
     */
    private Map<String, List<AddOnService>> servicesByReservation;

    /**
     * Initializes the service manager.
     */
    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    /**
     * Attaches a service to a reservation.
     * * @param reservationId confirmed reservation ID
     * @param service       add-on service
     */
    public void addService(String reservationId, AddOnService service) {
        // If the reservation doesn't have a list yet, create one. Then add the service.
        servicesByReservation.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
        System.out.println("Added '" + service.getServiceName() + "' ($" + service.getCost() + ") to Reservation: " + reservationId);
    }

    /**
     * Calculates total add-on cost
     * for a reservation.
     * * @param reservationId reservation ID
     * @return total service cost
     */
    public double calculateTotalServiceCost(String reservationId) {
        List<AddOnService> services = servicesByReservation.getOrDefault(reservationId, new ArrayList<>());
        double totalCost = 0.0;

        for (AddOnService service : services) {
            totalCost += service.getCost();
        }

        return totalCost;
    }
}

/**
 * ===============================================
 * MAIN CLASS - UseCase7AddOnServiceSelection
 * ===============================================
 * Use Case 7: Add-On Service Selection
 * * Description:
 * This class demonstrates how optional
 * services can be attached to a confirmed
 * booking.
 * * Services are added after room allocation
 * and do not affect inventory.
 * * @version 7.0
 */
public class UseCase7AddOnServiceSelection {

    /**
     * Application entry point.
     * * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("==== Use Case 7: Add-On Services Demo ====\n");

        // 1. Initialize the manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // 2. Create some available services
        AddOnService breakfast = new AddOnService("Breakfast Buffet", 25.0);
        AddOnService spa = new AddOnService("Spa Treatment", 120.0);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 50.0);

        // 3. Simulate adding services to confirmed Room IDs (from Use Case 6)
        String guest1RoomId = "Single-1";
        String guest2RoomId = "Suite-1";

        System.out.println("Booking Add-ons for Guest 1 (" + guest1RoomId + "):");
        serviceManager.addService(guest1RoomId, breakfast);
        serviceManager.addService(guest1RoomId, airportPickup);

        System.out.println("\nBooking Add-ons for Guest 2 (" + guest2RoomId + "):");
        serviceManager.addService(guest2RoomId, spa);
        serviceManager.addService(guest2RoomId, breakfast);

        // 4. Calculate and display total add-on costs
        System.out.println("\n==== Final Add-On Costs ====");
        System.out.println("Total extra cost for " + guest1RoomId + ": $" + serviceManager.calculateTotalServiceCost(guest1RoomId));
        System.out.println("Total extra cost for " + guest2RoomId + ": $" + serviceManager.calculateTotalServiceCost(guest2RoomId));
    }
}