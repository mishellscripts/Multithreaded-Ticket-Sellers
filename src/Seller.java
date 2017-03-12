import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Seller implements Runnable {
	Queue<Customer> customers;
	String type;
	Lock lock;
	Condition cond;

	public Seller(String t) {
		customers = new LinkedList<Customer>();
		type = t;
		lock = new ReentrantLock();
		cond = lock.newCondition();
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
