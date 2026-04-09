import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;

/**
 * ===============================================
 * ROOM MODELS (Use Cases 1 & 2)
 * ===============================================
 */
abstract class room {
    protected int numberOfbeds;
    protected int squareFeet;
    protected double pricePerNight;

    public room(int numberOfbeds, int squareFeet, double pricePerNight) {
        this.numberOfbeds = numberOfbeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    abstract void displayRoomDetails();
}

class SingleRoom extends room {
    public SingleRoom() {
        super(1, 250, 15000.0);
    }
    void displayRoomDetails() {
        System.out.println("Single Room:");
        System.out.println("Beds:" + numberOfbeds + " | Size:" + squareFeet + " | Price:" + pricePerNight);
    }
}

class DoubleRoom extends room {
    public DoubleRoom() {
        super(2, 400, 25000.0);
    }
    void displayRoomDetails() {
        System.out.println("Double Room:");
        System.out.println("Beds:" + numberOfbeds + " | Size:" + squareFeet + " | Price:" + pricePerNight);
    }
}

class SuiteRoom extends room {
    public SuiteRoom() {
        super(3, 800, 50000.0);
    }
    void displayRoomDetails() {
        System.out.println("Suite Room:");
        System.out.println("Beds:" + numberOfbeds + " | Size:" + squareFeet + " | Price:" + pricePerNight);
    }
}

/**
 * ===============================================
 * INVENTORY & SEARCH (Use Cases 3 & 4)
 * ===============================================
 */
class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
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

class RoomSearchService {
    public void searchAvailableRooms(RoomInventory inventory) {
        Map<String, Integer> availability = inventory.getRoomAvailability();
        System.out.println("\n==== Room Search & Availability ====");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            if (entry.getValue() > 0) {
                System.out.println(entry.getKey() + " Room - Available: " + entry.getValue());
            }
        }
    }
}

/**
 * ===============================================
 * RESERVATION & QUEUE (Use Case 5)
 * ===============================================
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}

/**
 * ===============================================
 * ALLOCATION SERVICE (Use Case 6)
 * ===============================================
 */
class RoomAllocationService {
    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    // Returns the generated Room ID if successful, or null if failed
    public String allocateRoom(Reservation reservation, RoomInventory inventory) {
        String type = reservation.getRoomType();
        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (availability.containsKey(type) && availability.get(type) > 0) {
            String roomId = generateRoomId(type);

            allocatedRoomIds.add(roomId);
            assignedRoomsByType.computeIfAbsent(type, k -> new HashSet<>()).add(roomId);

            // Decrease inventory
            inventory.updateAvailability(type, availability.get(type) - 1);

            System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() + ", Room ID: " + roomId);
            return roomId;
        } else {
            System.out.println("Booking failed for " + reservation.getGuestName() + ": No " + type + " available.");
            return null;
        }
    }

    private String generateRoomId(String roomType) {
        int currentCount = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size();
        return roomType + "-" + (currentCount + 1);
    }
}

/**
 * ===============================================
 * ADD-ON SERVICES (Use Case 7)
 * ===============================================
 */
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }
    public String getServiceName() { return serviceName; }
    public double getCost() { return cost; }
}

class AddOnServiceManager {
    private Map<String, List<AddOnService>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        servicesByReservation.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
        System.out.println("   + Added '" + service.getServiceName() + "' ($" + service.getCost() + ") to Room: " + reservationId);
    }

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
 * HISTORY & REPORTING (Use Case 8)
 * ===============================================
 */
class BookingHistory {
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

class BookingReportService {
    public void generateReport(BookingHistory history) {
        System.out.println("\n==== Booking History Report ====");
        for (Reservation reservation : history.getConfirmedReservations()) {
            System.out.println("Guest: " + reservation.getGuestName() + ", Room Type: " + reservation.getRoomType());
        }
    }
}

/**
 * ===============================================
 * MAIN APPLICATION
 * ===============================================
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println(" Welcome to BookMyStay Management System");
        System.out.println("==========================================\n");

        // 1. Initialize Core Services
        RoomInventory inventory = new RoomInventory();
        RoomSearchService searchService = new RoomSearchService();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService();
        AddOnServiceManager addOnManager = new AddOnServiceManager();
        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // 2. Display initial inventory
        searchService.searchAvailableRooms(inventory);

        // 3. Create Incoming Booking Requests
        System.out.println("\n==== Receiving Booking Requests ====");
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);
        System.out.println("3 requests added to the processing queue.");

        // 4. Process Queue, Allocate Rooms, and Log to History
        System.out.println("\n==== Processing Allocations ====");

        // We'll store mapped Room IDs to add services to them later
        Map<String, String> guestToRoomIdMap = new HashMap<>();

        while (bookingQueue.hasPendingRequests()) {
            Reservation currentRequest = bookingQueue.getNextRequest();
            String allocatedRoomId = allocationService.allocateRoom(currentRequest, inventory);

            // If allocation was successful, add to history
            if (allocatedRoomId != null) {
                bookingHistory.addReservation(currentRequest);
                guestToRoomIdMap.put(currentRequest.getGuestName(), allocatedRoomId);
            }
        }

        // 5. Attach Add-On Services for confirmed guests
        System.out.println("\n==== Selecting Add-On Services ====");
        AddOnService breakfast = new AddOnService("Breakfast Buffet", 25.0);
        AddOnService spa = new AddOnService("Spa Treatment", 120.0);

        // Add breakfast for Abhi
        String abhiRoom = guestToRoomIdMap.get("Abhi");
        if (abhiRoom != null) addOnManager.addService(abhiRoom, breakfast);

        // Add spa for Vanmathi
        String vanmathiRoom = guestToRoomIdMap.get("Vanmathi");
        if (vanmathiRoom != null) addOnManager.addService(vanmathiRoom, spa);

        // Print extra costs
        System.out.println("\nTotal Add-On Cost for Abhi: $" + addOnManager.calculateTotalServiceCost(abhiRoom));
        System.out.println("Total Add-On Cost for Vanmathi: $" + addOnManager.calculateTotalServiceCost(vanmathiRoom));

        // 6. Generate Final Use Case 8 Report
        reportService.generateReport(bookingHistory);

        // 7. Final Inventory Check
        System.out.println("\n==== Final Inventory Status ====");
        for (String roomType : inventory.getRoomAvailability().keySet()) {
            System.out.println(roomType + " -> " + inventory.getRoomAvailability().get(roomType));
        }
    }
}
