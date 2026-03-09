//use case 3
//author - pranat
//version 3.1

import java.util.HashMap;
import java.util.Map;

abstract class room{
    protected int numberOfbeds;
    protected int squareFeet;
    protected double pricePerNight;

    public room(int numberOfbeds,int squareFeet,double pricePerNight){
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
 *
 */
class SingleRoom extends room{

    public SingleRoom(){
        super(1,250,15000.0);
    }

    void displayRoomDetails(){
        System.out.println("Single Room:");
        System.out.println("Beds:" + numberOfbeds);
        System.out.println("Size" + squareFeet);
        System.out.println("Price Per Night:"+pricePerNight);
        System.out.println("Available" + 5 + "\n");
    }
}

/**
 * ===============================================
 * class double room
 * ===============================================
 * represent a double rooom in the hotel
 * @version 2.1
 *
 */
class DoubleRoom extends room{

    public DoubleRoom(){
        super(1,250,15000.0);
    }

    void displayRoomDetails(){
        System.out.println("Double Room:");
        System.out.println("Beds:" + numberOfbeds);
        System.out.println("Size" + squareFeet);
        System.out.println("Price Per Night:"+pricePerNight);
        System.out.println("Available" + 3 + "\n");
    }
}

/**
 * ===============================================
 * class suite room
 * ===============================================
 * represent a suite rooom in the hotel
 * @version 2.1
 *
 */
class SuiteRoom extends room{

    public SuiteRoom(){
        super(1,250,15000.0);
    }

    void displayRoomDetails(){
        System.out.println("SuiteRoom:");
        System.out.println("Beds:" + numberOfbeds);
        System.out.println("Size" + squareFeet);
        System.out.println("Price Per Night:"+pricePerNight);
        System.out.println("Available" + 2 + "\n");
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

    /*
     * Stores available room count for each room type.
     * Key -> Room type name
     * Value -> Available room count
     */
    private Map<String, Integer> roomAvailability;

    /*
     * Constructor initializes the inventory
     * with default availability values.
     */
    public RoomInventory(){
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    /*
     * Initializes room availability data
     */
    private void initializeInventory(){
        roomAvailability.put("Single Room",5);
        roomAvailability.put("Double Room",3);
        roomAvailability.put("Suite Room",2);
    }

    /*
     * Returns current availability map
     */
    public Map<String,Integer> getRoomAvailability(){
        return roomAvailability;
    }

    /*
     * Updates availability for a room type
     */
    public void updateAvailability(String roomType,int count){
        roomAvailability.put(roomType,count);
    }
}

/**
 * =================================================
 * MAIN CLASS – UseCase3InventorySetup
 * =================================================
 * Use Case 3: Centralized Room Inventory Management
 * @version 3.1
 */
class UseCase3InventorySetup {

    public static void main(String[] args){

        System.out.println("welcome to hotel booking management system");
        System.out.println("System initialized successfully");
        System.out.println("Hotel Room Initialization\n");

        SingleRoom singleRoom1 = new SingleRoom();
        DoubleRoom doubleRoom1 = new DoubleRoom();
        SuiteRoom suiteRoom1 = new SuiteRoom();

        singleRoom1.displayRoomDetails();
        doubleRoom1.displayRoomDetails();
        suiteRoom1.displayRoomDetails();

        System.out.println("Centralized Room Inventory\n");

        RoomInventory inventory = new RoomInventory();

        Map<String,Integer> availability = inventory.getRoomAvailability();

        for(String roomType : availability.keySet()){
            System.out.println(roomType + " -> " + availability.get(roomType));
        }

        System.out.println("\nUpdating Inventory...");

        inventory.updateAvailability("Single Room",4);

        for(String roomType : availability.keySet()){
            System.out.println(roomType + " -> " + availability.get(roomType));
        }
    }
}