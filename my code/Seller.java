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
	
	protected long past_time;
	protected int elapse_time;
	protected long currentTime;
	//String type;
	protected Seat[][] seating;
	private Object lock;

	public Seller(Seat[][] s, int serviceTime, String sellerID, Object lk, long current_time) {
		customers = new LinkedList<Customer>();
		this.serviceTime = serviceTime;
		seating = s;
		lock = lk;
		this.sellerID = sellerID;
		this.past_time = current_time;
		
	}
	
	protected void calTime(Customer customer){
		
		time = (int)(currentTime + serviceTime); //+ elapse_time;
		//System.out.println("----------------" + customer.getArrivalTime() + "---sid: " + this.sellerID + "service: " +this.serviceTime);
		customer.setTime(time);
	}
	
	
	protected void printMsg(Customer customer, Seat seat){
		int hour = customer.getTime() / 60;
		int min = customer.getTime() % 60;
		String time = "";
		if(min < 10) time = hour + ":0" + min;
		else time = hour + ":" + min;
		if (seat == null) System.out.println(time + "  " + sellerID + " - Sorry, the concert is sold out!");
		else System.out.println(time + "  " + sellerID + " - Success! Your seat is " + seat.getSeatNumber());
		
		printSeating(this.seating, 10, 10);

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

	protected void update(){
		
		currentTime = System.currentTimeMillis() - this.past_time;
		if(currentTime < 1000)
			currentTime = 0;
		else
			currentTime /= 1000;
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
	public static void printSeating(Seat[][] seating, int maxRows, int maxCols)
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		for (int row = 0; row < maxRows; row++)
		{
			for (int col = 0; col < maxCols; col++)
			{
				if (seating[row][col].isSeatEmpty()) 
					System.out.printf("%7s ", "-");
				else 
					System.out.printf("%7s ", seating[row][col].getCustomer().getTicket());
			}
			System.out.println();
		}
	}
	
}
