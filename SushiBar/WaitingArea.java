import java.util.LinkedList;

/**
 * This class implements a waiting area used as the bounded buffer, in the producer/consumer problem.
 */
public class WaitingArea {

    /**
     * Creates a new waiting area.
     * areaSize decides how many people can be waiting at the same time (how large the shared buffer is)
     *
     * @param size The maximum number of Customers that can be waiting.
     */

    private LinkedList<Customer> list;
    public int WaitingAreaSize;

    public WaitingArea(int size) {
        // TODO Implement required functionality
        WaitingAreaSize = size;
        this.list = new LinkedList<>();
    }

    /**
     * This method should put the customer into the waitingArea
     *
     * @param customer A customer created by Door, trying to enter the waiting area
     */
    public synchronized void enter(Customer customer) throws InterruptedException{
        // TODO Implement required functionality
        //If the queue is full, wait();
        if(!SushiBar.isOpen){
            System.out.println("Im waking errone up");
            notifyAll();
        }
        while(list.size() == WaitingAreaSize){
            this.wait();
        }
        this.list.add(customer);
        SushiBar.write(Thread.currentThread().getName() + ": Customer" + customer.getCustomerID() + " is now waiting.");
        this.notifyAll();
    }

    /**
     * @return The customer that is first in line.
     */
    public synchronized Customer next() throws InterruptedException{
        // TODO Implement required functionality
        //While the queue is empty, wait();
        while (list.size() == 0){
            this.wait();
        }
        Customer customer = list.pop();
        this.notify();
        SushiBar.write(Thread.currentThread().getName() + ": Customer" + customer.getCustomerID() + " is now fetched");
        return customer;
        }


    public int getQueueSize() {
        return list.size();
    }
    // Add more methods as you see fit


}
