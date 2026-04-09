import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;

/**
 * ===============================================
 * CUSTOM EXCEPTIONS & VALIDATION (Use Case 9)
 * ===============================================
 */
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class ReservationValidator {
    public void validate(String guestName, String roomType, RoomInventory inventory) throws InvalidBookingException {
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }
        if (!inventory.getRoomAvailability().containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }
        if (inventory.getRoomAvailability().get(roomType) <= 0) {
            throw new InvalidBookingException("No " + roomType + " rooms are currently available.");
        }
    }
}

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
    public SingleRoom() { super(1, 250, 15000.0); }
    void displayRoomDetails() {
        System.out.println("Single Room: Beds:" + numberOfbeds + " | Size:" + squareFeet + " | Price:" + pricePerNight);
    }
}

class DoubleRoom extends room {
    public DoubleRoom() { super(2, 400, 25000.0); }
    void displayRoomDetails() {
        System.out.println("Double Room: Beds:" + numberOfbeds + " | Size:" + squareFeet + " | Price:" + pricePerNight);
    }
}

class SuiteRoom extends room {
    public SuiteRoom() { super(3, 800, 50000.0); }
    void displayRoomDetails() {
        System.out.println("Suite Room: Beds:" + numberOfbeds + " | Size:" + squareFeet + " | Price:" + pricePerNight);
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
        System.out.println("\n==== Current Room Availability ====");
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
    private Queue<Reservation> requestQueue = new LinkedList<>();

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
    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> assignedRoomsByType = new HashMap<>();

    public String allocateRoom(Reservation reservation, RoomInventory inventory) {
        String type = reservation.getRoomType();
        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (availability.containsKey(type) && availability.get(type) > 0) {
            int currentCount = assignedRoomsByType.getOrDefault(type, new HashSet<>()).size();
            String roomId = type + "-" + (currentCount + 1);

            allocatedRoomIds.add(roomId);
            assignedRoomsByType.computeIfAbsent(type, k -> new HashSet<>()).add(roomId);
            inventory.updateAvailability(type, availability.get(type) - 1);

            System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() + ", Room ID: " + roomId);
            return roomId;
        } else {
            System.out.println("Booking failed for " + reservation.getGuestName() + ": No " + type + " available.");
            return null;
        }
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
    private Map<String, List<AddOnService>> servicesByReservation = new HashMap<>();

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
    private List<Reservation> confirmedReservations = new ArrayList<>();

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

        // Initialize Core Services
        RoomInventory inventory = new RoomInventory();
        RoomSearchService searchService = new RoomSearchService();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService();
        AddOnServiceManager addOnManager = new AddOnServiceManager();
        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();
        ReservationValidator validator = new ReservationValidator();
        Scanner scanner = new Scanner(System.in);

        // Display initial inventory
        searchService.searchAvailableRooms(inventory);

        // ---------------------------------------------------------
        // USE CASE 9: Interactive Validation Demo
        // ---------------------------------------------------------
        System.out.println("\n==== Interactive Booking Validation ====");
        try {
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            // Validate the input centrally
            validator.validate(guestName, roomType, inventory);

            // If validation passes, add to queue
            Reservation newReservation = new Reservation(guestName, roomType);
            bookingQueue.addRequest(newReservation);
            System.out.println("Booking successful! Request added to queue.");

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        } finally {
            scanner.close(); // Close scanner to prevent memory leaks
        }

        // ---------------------------------------------------------
        // USE CASES 5-8: Automated Processing of Valid Queues
        // ---------------------------------------------------------
        System.out.println("\n==== Adding System Automated Requests ====");
        try {
            // These simulate hardcoded valid inputs bypassing the scanner
            Reservation r1 = new Reservation("Subha", "Double");
            Reservation r2 = new Reservation("Vanmathi", "Suite");

            validator.validate(r1.getGuestName(), r1.getRoomType(), inventory);
            bookingQueue.addRequest(r1);

            validator.validate(r2.getGuestName(), r2.getRoomType(), inventory);
            bookingQueue.addRequest(r2);

            System.out.println("Automated requests added successfully.");
        } catch (InvalidBookingException e) {
            System.out.println("System Automated Booking failed: " + e.getMessage());
        }

        // Process Queue, Allocate Rooms, and Log to History
        System.out.println("\n==== Processing Allocations ====");
        Map<String, String> guestToRoomIdMap = new HashMap<>();

        while (bookingQueue.hasPendingRequests()) {
            Reservation currentRequest = bookingQueue.getNextRequest();
            String allocatedRoomId = allocationService.allocateRoom(currentRequest, inventory);

            if (allocatedRoomId != null) {
                bookingHistory.addReservation(currentRequest);
                guestToRoomIdMap.put(currentRequest.getGuestName(), allocatedRoomId);
            }
        }

        // Attach Add-On Services
        System.out.println("\n==== Selecting Add-On Services ====");
        AddOnService breakfast = new AddOnService("Breakfast Buffet", 25.0);
        AddOnService spa = new AddOnService("Spa Treatment", 120.0);

        String subhaRoom = guestToRoomIdMap.get("Subha");
        if (subhaRoom != null) addOnManager.addService(subhaRoom, breakfast);

        String vanmathiRoom = guestToRoomIdMap.get("Vanmathi");
        if (vanmathiRoom != null) addOnManager.addService(vanmathiRoom, spa);

        // Generate Reports
        reportService.generateReport(bookingHistory);

        System.out.println("\n==== Final Inventory Status ====");
        for (String type : inventory.getRoomAvailability().keySet()) {
            System.out.println(type + " -> " + inventory.getRoomAvailability().get(type));
        }
    }
}