import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Seller implements Runnable {
	Queue<Customer> customers;
<<<<<<< HEAD

	//String type;
	Seat[][] seating;


	public Seller(Seat[][] s) {
		customers = new LinkedList<Customer>();

=======
	String type;
	Seat[][] seats;

	public Seller(String t, Seat[][] s) {
	Lock lock;
	Condition cond;
	//String type;
	Seat[][] seating;

	public Seller(Seat[][] s) {
		customers = new LinkedList<Customer>();
		type = t;
		seats=s;
		lock = new ReentrantLock();
		cond = lock.newCondition();
>>>>>>> 43cf88a7ecd871f96f7e46ace10afccb20523ba6
		//type = t;
		seating = s;
	}
	
	public void addCustomer(Customer c)
	{
		customers.add(c);
<<<<<<< HEAD
	}
	
	public void sortQueue(){
		
=======
>>>>>>> 43cf88a7ecd871f96f7e46ace10afccb20523ba6
	}

	// seller thread to serve one time slice (1 minute)
	public void sell() throws InterruptedException {
		while (!customers.isEmpty()) {
			Object obj = new Object();
			synchronized(obj){
				
				
			Object lock = new Object();
			synchronized(lock) {
				while (customers.isEmpty()) wait();
				// Get customer in queue that is ready
				Customer customer = customers.peek();
				
				notifyAll();
			}
		}

		/*lock.lock();

		try {
			while (!customers.isEmpty()) {
				cond.await();
				// Get buyer in queue that is ready
				Customer customer = customers.peek();
				// Give seat
			}
			cond.signal();
		}
		finally {
			lock.unlock();
		}*/
	}

	public void run() {
		
	}
}
