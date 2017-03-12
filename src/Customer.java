import java.util.Random;

public class Customer {
	Random r = new Random();
	private int arrivalTime;
	private int seatNum;
	private int customerID;
	
	public Customer(int customerID){
		arrivalTime = r.nextInt();
		this.customerID = customerID;
	}
	
	public int getArrivalTime(){
		return this.arrivalTime;
	}
	
	public void setSeatNum(int seatNum){
		this.seatNum = seatNum;
	}
	
	public int getSeatNum(){
		return this.seatNum;
	}
	
	public int customerID(){
		return this.customerID;
	}
}
