import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;



/**
 * This class implements a customer, which is used for holding data and update the statistics
 *
 */
public class Customer {

    /**
     *  Creates a new Customer.
     *  Each customer should be given a unique ID
     */

    private static AtomicInteger counter = new AtomicInteger(0);
    private int id;
    public int eatHere;
    public int takeAway;

    public Customer() {
        // TODO Implement required functionality
        id = counter.incrementAndGet();
    }


    /**
     * Here you should implement the functionality for ordering food as described in the assignment.
     */
    public synchronized void order(){
        // TODO Implement required functionality
        Random rand = new Random();
        eatHere = rand.nextInt(SushiBar.maxOrder); //Customer orders a random number of pieces, not exceeding maxOrders
        if (eatHere == 0){
            eatHere = 1;
        }
        takeAway = rand.nextInt(SushiBar.maxOrder - eatHere); //Customer chooses takeaway between 0 and maxorders - eaten
        //System.out.println("Customer ordered " + eatHere + " Pieces, and " + takeAway + " pieces as takeaway");
    }

    /**
     *
     * @return Should return the customerID
     */
    public int getCustomerID() {
        // TODO Implement required functionality
        return id;

    }


    // Add more methods as you see fit
}
