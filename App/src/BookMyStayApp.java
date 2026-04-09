import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;

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

/**
 * ===============================================
 * class single room
 * ===============================================
 * represent a single rooom in the hotel
 * @version 2.1
 */
class SingleRoom extends room {

    public SingleRoom() {
        super(1, 250, 15000.0);
    }

    void displayRoomDetails() {
        System.out.println("Single Room:");
        System.out.println("Beds:" + numberOfbeds);
        System.out.println("Size:" + squareFeet);
        System.out.println("Price Per Night:" + pricePerNight);
        System.out.println("Available:" + 5 + "\n");
    }
}

/**
 * ===============================================
 * class double room
 * ===============================================
 * represent a double rooom in the hotel
 * @version 2.1
 */
class DoubleRoom extends room {

    public DoubleRoom() {
        super(1, 250, 15000.0);
    }

    void displayRoomDetails() {
        System.out.println("Double Room:");
        System.out.println("Beds:" + numberOfbeds);
        System.out.println("Size:" + squareFeet);
        System.out.println("Price Per Night:" + pricePerNight);
        System.out.println("Available:" + 3 + "\n");
    }
}

/**
 * ===============================================
 * class suite room
 * ===============================================
 * represent a suite rooom in the hotel
 * @version 2.1
 */
class SuiteRoom extends room {

    public SuiteRoom() {
        super(1, 250, 15000.0);
    }

    void displayRoomDetails() {
        System.out.println("Suite Room:");
        System.out.println("Beds:" + numberOfbeds);
        System.out.println("Size:" + squareFeet);
        System.out.println("Price Per Night:" + pricePerNight);
        System.out.println("Available:" + 2 + "\n");
    }
}

/**
 * =================================================
 * CLASS – RoomInventory
 * =================================================
 * Use Case 3: Centralized Room Inventory Management
 * @version 3.1
 */
class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("Single Room", 5);
        roomAvailability.put("Double Room", 3);
        roomAvailability.put("Suite Room", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

/**
 * =================================================
 * CLASS - RoomSearchService
 * =================================================
 * Use Case 4: Room Search & Availability Check
 * @version 4.0
 */
class RoomSearchService {
    public void searchAvailableRooms() {
        RoomInventory inventory = new RoomInventory();
        room SingleRoom = new SingleRoom();
        room doubleRoom = new DoubleRoom();
        room suiteRoom = new SuiteRoom();
        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("\n====Room Search====");
        if (availability.get("Single Room") > 0) {
            System.out.println("Single Room:");
            System.out.println("Beds:" + SingleRoom.numberOfbeds);
            System.out.println("Size:" + SingleRoom.squareFeet);
            System.out.println("Price Per Night:" + SingleRoom.pricePerNight);
            System.out.println("Available:" + availability.get("Single Room") + "\n");
        }
        if (availability.get("Suite Room") > 0) {
            System.out.println("Suite Room:");
            System.out.println("Beds:" + suiteRoom.numberOfbeds);
            System.out.println("Size:" + suiteRoom.squareFeet);
            System.out.println("Price Per Night:" + suiteRoom.pricePerNight);
            System.out.println("Available:" + availability.get("Suite Room") + "\n");
        }
        if (availability.get("Double Room") > 0) {
            System.out.println("Double Room:");
            System.out.println("Beds:" + doubleRoom.numberOfbeds);
            System.out.println("Size:" + doubleRoom.squareFeet);
            System.out.println("Price Per Night:" + doubleRoom.pricePerNight);
            System.out.println("Available:" + availability.get("Double Room") + "\n");
        }
    }
}

/**
 * ===============================================
 * CLASS – Reservation
 * ===============================================
 * Use Case 5: Booking Request (FIFO)
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * ===============================================
 * CLASS – BookingRequestQueue
 * ===============================================
 * Manages booking requests using FIFO
 */
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
 * =================================================
 * CLASS - RoomAllocationService
 * =================================================
 * Use Case 6: Reservation Confirmation & Room Allocation
 * @version 6.0
 */
class RoomAllocationService {

    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String type = reservation.getRoomType();
        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (availability.containsKey(type) && availability.get(type) > 0) {
            String roomId = generateRoomId(type);

            allocatedRoomIds.add(roomId);
            assignedRoomsByType.computeIfAbsent(type, k -> new HashSet<>()).add(roomId);

            inventory.updateAvailability(type, availability.get(type) - 1);

            System.out.println("Booking confirmed for Guest: " + reservation.getGuestName()
                    + ", Room ID: " + roomId);
        } else {
            System.out.println("Booking failed for " + reservation.getGuestName()
                    + ": No " + type + " available.");
        }
    }

    private String generateRoomId(String roomType) {
        int currentCount = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size();
        return roomType.split(" ")[0] + "-" + (currentCount + 1);
    }
}

/**
 * =================================
 * MAIN CLASS
 * =================================
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Welcome to hotel booking management system");
        System.out.println("System initialized successfully");
        System.out.println("\nHotel Room Initialization\n");

        SingleRoom singleRoom1 = new SingleRoom();
        DoubleRoom doubleRoom1 = new DoubleRoom();
        SuiteRoom suiteRoom1 = new SuiteRoom();

        singleRoom1.displayRoomDetails();
        doubleRoom1.displayRoomDetails();
        suiteRoom1.displayRoomDetails();

        System.out.println("Centralized Room Inventory\n");

        RoomInventory inventory = new RoomInventory();
        Map<String, Integer> availability = inventory.getRoomAvailability();

        for (String roomType : availability.keySet()) {
            System.out.println(roomType + " -> " + availability.get(roomType));
        }

        System.out.println("\nUpdating Inventory...");
        inventory.updateAvailability("Single Room", 4);

        for (String roomType : availability.keySet()) {
            System.out.println(roomType + " -> " + availability.get(roomType));
        }

        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms();

        System.out.println("\nBooking Request Queue & Room Allocation Processing");
        System.out.println("--------------------------------------------------");

        // Initialize queue and allocation service
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Create reservations (Updated to match Inventory Keys)
        Reservation r1 = new Reservation("Abhi", "Single Room");
        Reservation r2 = new Reservation("Subha", "Single Room");
        Reservation r3 = new Reservation("Vanmathi", "Suite Room");

        // Add to queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Process requests (FIFO + Allocation)
        while (bookingQueue.hasPendingRequests()) {
            Reservation r = bookingQueue.getNextRequest();
            allocationService.allocateRoom(r, inventory);
        }

        // Final Inventory Status
        System.out.println("\nFinal Inventory Status:");
        for (String roomType : inventory.getRoomAvailability().keySet()) {
            System.out.println(roomType + " -> " + inventory.getRoomAvailability().get(roomType));
        }
    }
}