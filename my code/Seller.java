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
	protected static Random r = new Random();
	
	private int serviceTime;

	//String type;
	protected Seat[][] seating;

	public Seller(Seat[][] s, int serviceTime) {
		customers = new LinkedList<Customer>();
		this.serviceTime = serviceTime;
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

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	

}
