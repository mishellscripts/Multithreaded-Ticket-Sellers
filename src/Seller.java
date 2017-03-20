import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public abstract class Seller implements Runnable {

	Queue<Customer> customers;
	protected static Random r = new Random();
	protected String sellerID;
	protected int serviceTime;
	protected int ticketNum = 1;

	//String type;
	protected Seat[][] seating;
	Object lock;

	public Seller(Seat[][] s, int serviceTime, String sellerID, Object lk) {
		customers = new LinkedList<Customer>();
		this.serviceTime = serviceTime;
		//type = t;
		seating = s;
		lock = lk;
		this.sellerID = sellerID;
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
	public abstract void sell();

	@Override
	public void run() {
		sell();
	}
}
