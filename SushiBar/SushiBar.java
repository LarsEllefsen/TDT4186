import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;


public class SushiBar {

    //SushiBar settings
    private static int waitingAreaCapacity = 20;
    private static int waitressCount = 2;
    private static int duration = 3;
    public static int maxOrder = 10;
    public static int waitressWait = 50; // Used to calculat the time the waitress spends before taking the order
    public static int customerWait = 300; // Used to calculate the time the customer uses eating
    public static int doorWait = 100; // Used to calculate the interval at which the door tries to create a customer
    public static boolean isOpen = true;
    private static int doorCount = 1;

    //Creating log file
    private static File log;
    private static String path = "./";

    //Variables related to statistics
    public static SynchronizedInteger customerCounter;
    public static SynchronizedInteger servedOrders;
    public static SynchronizedInteger takeawayOrders;
    public static SynchronizedInteger totalOrders;


    public static void main(String[] args) {
        log = new File(path + "log.txt");

        //Initializing shared variables for counting number of orders
        customerCounter = new SynchronizedInteger(0);
        totalOrders = new SynchronizedInteger(0);
        servedOrders = new SynchronizedInteger(0);
        takeawayOrders = new SynchronizedInteger(0);

        Clock clock = new Clock(10);

        //Create the waiting area (the shared buffer)
        WaitingArea waitingArea = new WaitingArea(waitingAreaCapacity);

        //Create the waitresses and start their threads
        Thread[] waitresses = new Thread[waitressCount];
        for (int i=0; i<waitresses.length; i++){
            waitresses[i] = new Thread(new Waitress(waitingArea),"Waitress " + i);
            waitresses[i].start();
        }

        //Create the doors and start their threads
        Thread[] doors = new Thread[doorCount];
        for (int i=0; i<doors.length; i++){
            doors[i] = new Thread(new Door(waitingArea),"Door " + i);
            doors[i].start();
        }

        try{
            mainHook(waitresses,doors);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //try{
          //  debug(t1,t2,t3);
        //} catch(InterruptedException e){
          //  System.out.println(e);
        //}wa

        // TODO initialize the bar and start the different threads
    }

    private static void mainHook(Thread[] waitresses, Thread[] doors) throws InterruptedException{
        for (Thread t: waitresses){
            t.join();
        }
        for (Thread t: doors){
            t.join();
        }
        System.out.println("Total Number of customers: " + customerCounter.get());
        System.out.println("Total Number of orders: " + totalOrders.get());
        System.out.println("Total Number of takeaway orders: " + takeawayOrders.get());
        System.out.println("Total Number of orders eaten here: " + servedOrders.get());
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        for (Thread t : threads) {
            System.out.println(t.getName() + " " + t.getState());
        }
    }

    public static void debug(Thread t1, Thread t2, Thread t3) throws InterruptedException{
        while (true) {
            System.out.println("Current state for the entrance is " + t1.getState());
            System.out.println("Current state for the waitress 1 is " + t2.getState());
            System.out.println("Current state for the waitress 2 is " + t3.getState());
            Thread.sleep(3000);
        }

    }

    //Writes actions in the log file and console
    public static void write(String str) {
        try {
            FileWriter fw = new FileWriter(log.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Clock.getTime() + ", " + str + "\n");
            bw.close();
            System.out.println(Clock.getTime() + ", " + str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
