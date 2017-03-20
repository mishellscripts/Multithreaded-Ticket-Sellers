import java.util.Random;

public class Customer implements Comparable<Customer>{
	Random r = new Random();
	private int arrivalTime;
	private int seatNum;
	private int customerID;
	private String ticket;
	
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Customer(int customerID){
		arrivalTime = r.nextInt(59);
		this.customerID = customerID;
		seatNum = -1;
	}
	
	public int getArrivalTime(){
		return this.arrivalTime;
	}
	
	public void setSeatNum(int seatNum){
		this.seatNum = seatNum;
	}
	
	public boolean isSigned(){
		if(seatNum== -1)  return false;
		else return true;
	}
	public int getSeatNum(){
		return this.seatNum;
	}
	
	public int customerID(){
		return this.customerID;
	}

	@Override
	public int compareTo(Customer customer) {
		if(this.arrivalTime < customer.arrivalTime)
			return -1;
		else if(this.arrivalTime > customer.arrivalTime)
			return 1;
		else
			return 0;
	}
}
