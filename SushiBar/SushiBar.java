import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class SushiBar {

    //SushiBar settings
    private static int waitingAreaCapacity = 20;
    private static int waitressCount = 10;
    private static int duration = 3;
    public static int maxOrder = 10;
    public static int waitressWait = 50; // Used to calculat the time the waitress spends before taking the order
    public static int customerWait = 3000; // Used to calculate the time the customer uses eating
    public static int doorWait = 100; // Used to calculate the interval at which the door tries to create a customer
    public static boolean isOpen = true;

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

        //Clock clock = new Clock(10);

        WaitingArea waitingArea = new WaitingArea(waitingAreaCapacity);
        Door entrance = new Door(waitingArea);

        Thread[] threads = new Thread[waitressCount];
        for (int i=0; i<threads.length; i++){
            threads[i] = new Thread(new Waitress(waitingArea),"Waitress " + i);
            threads[i].start();
        }

        Thread t1 = new Thread(entrance, "Entrance 1");
        t1.start();

        System.out.println("hei :P");
        //try{
          //  debug(t1,t2,t3);
        //} catch(InterruptedException e){
          //  System.out.println(e);
        //}




        // TODO initialize the bar and start the different threads
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
