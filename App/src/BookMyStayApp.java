import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ===============================================
 * USE CASE 9: Error Handling & Validation
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
 * USE CASES 1 & 2: Room Models
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
 * USE CASES 3 & 4: Inventory & Search
 * ===============================================
 */
class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        // Default startup values
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() { return roomAvailability; }
    public void updateAvailability(String roomType, int count) { roomAvailability.put(roomType, count); }
}

class RoomSearchService {
    public void searchAvailableRooms(RoomInventory inventory) {
        System.out.println("\n==== Current Room Availability ====");
        for (Map.Entry<String, Integer> entry : inventory.getRoomAvailability().entrySet()) {
            if (entry.getValue() > 0) {
                System.out.println(entry.getKey() + " Room - Available: " + entry.getValue());
            }
        }
    }
}

/**
 * ===============================================
 * USE CASE 5: Reservation & Queue
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

    public void addRequest(Reservation reservation) { requestQueue.offer(reservation); }
    public Reservation getNextRequest() { return requestQueue.poll(); }
    public boolean hasPendingRequests() { return !requestQueue.isEmpty(); }
}

/**
 * ===============================================
 * USE CASE 6: Allocation Service
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

            System.out.println(Thread.currentThread().getName() + " -> Booking confirmed for Guest: " + reservation.getGuestName() + ", Room ID: " + roomId);
            return roomId;
        } else {
            System.out.println(Thread.currentThread().getName() + " -> Booking failed for " + reservation.getGuestName() + ": No " + type + " available.");
            return null;
        }
    }
}

/**
 * ===============================================
 * USE CASE 7: Add-On Services
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
}

/**
 * ===============================================
 * USE CASE 8: History & Reporting
 * ===============================================
 */
class BookingHistory {
    private List<Reservation> confirmedReservations = new ArrayList<>();

    public synchronized void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }
    public List<Reservation> getConfirmedReservations() { return confirmedReservations; }
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
 * USE CASE 10: Cancellation & Rollback
 * ===============================================
 */
class CancellationService {
    private Stack<String> releasedRoomIds = new Stack<>();
    private Map<String, String> reservationRoomTypeMap = new HashMap<>();

    public synchronized void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    public synchronized void cancelBooking(String reservationId, RoomInventory inventory) {
        if (reservationRoomTypeMap.containsKey(reservationId)) {
            String roomType = reservationRoomTypeMap.get(reservationId);
            releasedRoomIds.push(reservationId);
            reservationRoomTypeMap.remove(reservationId);

            int currentCount = inventory.getRoomAvailability().getOrDefault(roomType, 0);
            inventory.updateAvailability(roomType, currentCount + 1);

            System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
        } else {
            System.out.println("Cancellation failed: Reservation ID not found.");
        }
    }

    public void showRollbackHistory() {
        System.out.println("\n==== Rollback History (Most Recent First) ====");
        for (int i = releasedRoomIds.size() - 1; i >= 0; i--) {
            System.out.println("Released Reservation ID: " + releasedRoomIds.get(i));
        }
    }
}

/**
 * ===============================================
 * USE CASE 11: Concurrent Booking Processor
 * ===============================================
 */
class ConcurrentBookingProcessor implements Runnable {
    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;
    private CancellationService cancellationService;
    private BookingHistory bookingHistory;
    private Map<String, String> globalGuestRoomMap;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue, RoomInventory inventory,
            RoomAllocationService allocationService, CancellationService cancellationService,
            BookingHistory bookingHistory, Map<String, String> globalGuestRoomMap) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
        this.cancellationService = cancellationService;
        this.bookingHistory = bookingHistory;
        this.globalGuestRoomMap = globalGuestRoomMap;
    }

    @Override
    public void run() {
        while (true) {
            Reservation reservation = null;

            synchronized (bookingQueue) {
                if (bookingQueue.hasPendingRequests()) {
                    reservation = bookingQueue.getNextRequest();
                } else {
                    break;
                }
            }

            if (reservation != null) {
                String allocatedRoomId = null;
                synchronized (inventory) {
                    allocatedRoomId = allocationService.allocateRoom(reservation, inventory);
                }

                if (allocatedRoomId != null) {
                    bookingHistory.addReservation(reservation);
                    cancellationService.registerBooking(allocatedRoomId, reservation.getRoomType());
                    globalGuestRoomMap.put(reservation.getGuestName(), allocatedRoomId);
                }

                try { Thread.sleep(50); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        }
    }
}

/**
 * ===============================================
 * USE CASE 12: File Persistence
 * ===============================================
 */
class FilePersistenceService {
    public void saveInventory(RoomInventory inventory, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Integer> entry : inventory.getRoomAvailability().entrySet()) {
                writer.println(entry.getKey() + "=" + entry.getValue());
            }
            System.out.println("\nInventory saved successfully to " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to save inventory: " + e.getMessage());
        }
    }

    public void loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean dataLoaded = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String roomType = parts[0];
                    int count = Integer.parseInt(parts[1]);
                    inventory.updateAvailability(roomType, count);
                    dataLoaded = true;
                }
            }
            if (dataLoaded) {
                System.out.println("Inventory successfully loaded from file.");
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("No valid inventory data found. Starting fresh.");
        }
    }
}

/**
 * ===============================================
 * MAIN APPLICATION FLOW
 * ===============================================
 */
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println(" Welcome to BookMyStay Management System");
        System.out.println("==========================================\n");

        // Initialize Services
        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistenceService = new FilePersistenceService();
        RoomSearchService searchService = new RoomSearchService();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService();
        AddOnServiceManager addOnManager = new AddOnServiceManager();
        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();
        ReservationValidator validator = new ReservationValidator();
        CancellationService cancellationService = new CancellationService();

        String persistenceFilePath = "inventory_data.txt";

        // --- STEP 1: System Recovery / Load Persistence (UC 12) ---
        System.out.println("==== System Recovery ====");
        persistenceService.loadInventory(inventory, persistenceFilePath);
        searchService.searchAvailableRooms(inventory);

        // --- STEP 2: Adding System Requests with Validation (UC 9 & 5) ---
        System.out.println("\n==== Adding System Requests ====");
        Map<String, String> guestToRoomIdMap = new ConcurrentHashMap<>(); // Thread-safe map

        Reservation[] requests = {
                new Reservation("Abhi", "Single"),
                new Reservation("Vanmathi", "Double"),
                new Reservation("Kural", "Suite"),
                new Reservation("Subha", "Single"),
                new Reservation("InvalidUser", "Penthouse") // Will fail validation gracefully
        };

        for (Reservation req : requests) {
            try {
                validator.validate(req.getGuestName(), req.getRoomType(), inventory);
                bookingQueue.addRequest(req);
            } catch (InvalidBookingException e) {
                System.out.println("Validation failed for " + req.getGuestName() + ": " + e.getMessage());
            }
        }

        // --- STEP 3: Concurrent Allocation (UC 11 & 6) ---
        System.out.println("\n==== Starting Concurrent Processing ====");
        Thread t1 = new Thread(new ConcurrentBookingProcessor(
                bookingQueue, inventory, allocationService, cancellationService, bookingHistory, guestToRoomIdMap), "Thread-1");
        Thread t2 = new Thread(new ConcurrentBookingProcessor(
                bookingQueue, inventory, allocationService, cancellationService, bookingHistory, guestToRoomIdMap), "Thread-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Processing interrupted.");
        }

        // --- STEP 4: Add-On Services (UC 7) ---
        System.out.println("\n==== Selecting Add-On Services ====");
        AddOnService breakfast = new AddOnService("Breakfast Buffet", 25.0);
        String abhiRoom = guestToRoomIdMap.get("Abhi");
        if (abhiRoom != null) addOnManager.addService(abhiRoom, breakfast);

        // --- STEP 5: Cancellation & Rollback (UC 10) ---
        System.out.println("\n==== Processing Cancellations ====");
        String subhaRoom = guestToRoomIdMap.get("Subha");
        if (subhaRoom != null) {
            cancellationService.cancelBooking(subhaRoom, inventory);
        }

        // --- STEP 6: Reporting (UC 8 & 10) ---
        reportService.generateReport(bookingHistory);
        cancellationService.showRollbackHistory();

        // --- STEP 7: Save System State (UC 12) ---
        System.out.println("\n==== Final Inventory Status ====");
        for (String type : inventory.getRoomAvailability().keySet()) {
            System.out.println(type + " -> " + inventory.getRoomAvailability().get(type));
        }

        persistenceService.saveInventory(inventory, persistenceFilePath);
    }
}