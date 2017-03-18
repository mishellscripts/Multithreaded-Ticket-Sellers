import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Seller implements Runnable {

	Queue<Customer> customers;
	protected static Random r = new Random();
	
	private int serviceTime;

	//String type;
	protected Seat[][] seating;
	Object lock;

	public Seller(Seat[][] s, int serviceTime, Object lk) {
		customers = new LinkedList<Customer>();
		this.serviceTime = serviceTime;
		//type = t;
		seating = s;
		lock = lk;
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
			//Object lock = new Object();
			
			while (customers.isEmpty()) return;
			// Get customer in queue that is ready
			Customer customer = customers.peek();

			// Find seat for the customer
			// Case for Seller H
			boolean found = false;
			
			synchronized(lock) {
				find_seat:
				for (int i = 0; i < seating.length; i++) {
					for (int j = 0; j < seating[0].length; j++) {
						if (seating[i][j].isSeatEmpty()) {
							// Assign seat to customer
							// Seat number = (Row x 10) + (Col + 1)
							int seatNum = (i*10)+j+1;
							Seat seat = new Seat(seatNum);
							seat.assignSeat(customer);
							seating[i][j] = seat;
							found = true;
							break find_seat;
						}
					}
				}
			}
			
			if (!found) System.out.println("Sorry, the concert is sold out!");

			notifyAll();
			customers.remove();
			
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			sell();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
