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
	protected int time = 0;

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
	
	protected void calTime(Customer customer){
		time = customer.getArrivalTime() + serviceTime;
		customer.setTime(time);
	}
	
	protected void printMsg(Customer customer, Seat seat){
		int min = customer.getTime() / 60;
		int sec = customer.getTime() % 60;
		String time = "";
		if(sec < 10) time = min + ":0" + sec;
		else time = min + ":" + sec;
		if (seat == null) System.out.println(time + "  " + sellerID + " - Sorry, the concert is sold out!");
		else System.out.println(time + "  " + sellerID + " - Success! Your seat is " + seat.getSeatNumber());

	}
	
	protected void assignSeat(Customer customer, Seat seat, int i, int j){
		if(ticketNum < 10)
			customer.setTicket(sellerID + "0" + ticketNum);
		else
			customer.setTicket(sellerID + ticketNum);
		calTime(customer);
		ticketNum++;
		seat.assignSeat(customer);
		seating[i][j] = seat;
	}

	public void addCustomer(Customer c)
	{
		customers.add(c);
	}

	public void sortQueue(){
		Customer [] temp = customers.toArray(new Customer[customers.size()]);
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
