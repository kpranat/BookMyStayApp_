//use case 2
//author - pranat
//version 2.1
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
class BookMyStayApp{
    public static void main (String[] args){
        System.out.println("welcome to hotel booking management system");
        System.out.println("System initialized successfully");
        System.out.println("Hotel Room Initialization\n");
        SingleRoom singleRoom1 = new SingleRoom();
        DoubleRoom doubleRoom1 = new DoubleRoom();
        SuiteRoom suiteRoom1 = new SuiteRoom();
        singleRoom1.displayRoomDetails();
        doubleRoom1.displayRoomDetails();
        suiteRoom1.displayRoomDetails();


    }
}