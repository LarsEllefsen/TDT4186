
/**
 * This class implements the consumer part of the producer/consumer problem.
 * One waitress instance corresponds to one consumer.
 */
public class Waitress implements Runnable {

    /**
     * Creates a new waitress. Make sure to save the parameter in the class
     *
     * @param waitingArea The waiting area for customers
     */
    private WaitingArea customerQueue;
    private Customer customer;

    Waitress(WaitingArea waitingArea) {
        // TODO Implement required functionality
        this.customerQueue = waitingArea;
    }

    /**
     * This is the code that will run when a new thread is
     * created for this instance
     */
    @Override
    public void run() {
        // TODO Implement required functionality
        while (SushiBar.isOpen) {
            try{
                if (SushiBar.isOpen){
                    customer = customerQueue.next(); //Fetch the first customer in the line
                    if (customer != null){
                        Thread.sleep(SushiBar.waitressWait); //Sleep a little I guess, you dont get paid enough
                        customer.order(); //Customer orders a random amount of sushi
                        SushiBar.write(Thread.currentThread().getName() + ": Customer" + customer.getCustomerID() + " is now eating.");

                        SushiBar.takeawayOrders.add(customer.takeAway); //Add the takeaway orders
                        SushiBar.servedOrders.add(customer.eatHere); //Add the eaten here orders
                        SushiBar.totalOrders.add(customer.eatHere + customer.takeAway); //Add them to the total number of orders
                        SushiBar.customerCounter.increment(); //Increment the customer counter by 1.

                        Thread.sleep(customer.eatHere*SushiBar.customerWait); //Wait X amount of time for each sushi piece ordered to eat in the bar
                        SushiBar.write(Thread.currentThread().getName() + ": Customer" + customer.getCustomerID() + " is now leaving.");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        customerQueue.releaseLocks();
        System.out.println(Thread.currentThread().getName() + " is done");

    }


}
