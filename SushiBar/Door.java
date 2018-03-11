import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class implements the Door component of the sushi bar assignment
 * The Door corresponds to the Producer in the producer/consumer problem
 */


public class Door implements Runnable {

    /**
     * Creates a new Door. Make sure to save the
     * @param waitingArea   The customer queue waiting for a seat
     */

    private WaitingArea customerQueue;

    public Door(WaitingArea waitingArea) {
        // TODO Implement required functionality
        this.customerQueue = waitingArea;
    }
//Thread.sleep(rand.nextInt(SushiBar.doorWait));
    /**
     * This method will run when the door thread is created (and started)
     * The method should create customers at random intervals and try to put them in the customerQueue
     */
    @Override
    public void run() {
        // TODO Implement required functionality
        Random rand = new Random();
        while (SushiBar.isOpen) {
            try {
                //Creates a new customer in the interval between doorWait and 3000ms.
                Thread.sleep(ThreadLocalRandom.current().nextInt(SushiBar.doorWait, 3000));
                customerQueue.enter(new Customer());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    // Add more methods as you see fit
}
