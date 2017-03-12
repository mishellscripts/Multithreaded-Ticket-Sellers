import java.util.LinkedList;
import java.util.Queue;

public class Seller implements Runnable {
	Queue<Customer> customers;

	public Seller() {
		customers = new LinkedList<Customer>();
	}

	public void sell() {
		while (!customers.isEmpty()) {

		}
	}
	public void run() {

	}
}
