import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Seller implements Runnable {
	Queue<Customer> customers;
	protected Random r = new Random();
	//String type;
	protected Seat[][] seating;

	public Seller(Seat[][] s) {
		customers = new LinkedList<Customer>();
		
		//type = t;
		seating = s;
	}
	
	public void addCustomer(Customer c)
	{
		customers.add(c);
	}
	
	public void sortQueue(){
		Customer [] temp = (Customer[]) customers.toArray();
		customers.clear();
		Arrays.sort(temp);
		for(Customer c: temp)
			customers.add(c);
	}

	// seller thread to serve one time slice (1 minute)
	public void sell() throws InterruptedException {
		while (!customers.isEmpty()) {		
				
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
