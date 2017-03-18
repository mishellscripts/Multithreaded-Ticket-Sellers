import java.util.Random;

public class SellerM extends Seller{

	//Seat[][] seating;
	private int serviceTime;


	public SellerM(Seat[][] s) {
		// Seller H takes 1 or 2 minutes to complete a ticket sale
		super(s, r.nextInt(4) + 2);
	}

	public void sell() throws InterruptedException {
		while (!customers.isEmpty()) {						
			Object lock = new Object();
			synchronized(lock) {
				while (customers.isEmpty()) wait();
				// Get customer in queue that is ready
				Customer customer = customers.peek();

				// Find seat for the customer
				// Case for Seller H
				boolean found = false;
				boolean addOrSub = true; // True = Add, False = Subtract
				int addSubCount = 0;
				int currentRow = 5;
				
				while (addSubCount <= 8) {
					if (addOrSub) currentRow += addSubCount;
					else currentRow -= addSubCount;
				    addSubCount += 1;
				}
				
				
				boolean flag = true;
				int counter = 1;
				find_seat:
				for(int i = 5; i >= 0 && i < seating.length;) {
					for (int j = seating[0].length - 1; j >= 0 || j < seating[0].length;) {
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
						if(flag == true){
							j--;
						}
						else{
							j++;
						}
					}
					if(flag == true){
						i += counter;
						flag = false;
					}
					else{
						i -= counter;
						flag = true;
					}
					counter++;
				}
					

				if (!found) System.out.println("Sorry, the concert is sold out. Please come again!");

				notifyAll();
			}
		}
	}
	
	public static void main(String[] args) {
		boolean found = false;
		boolean addOrSub = true; // True = Add, False = Subtract
		int addSubCount = 0;
		int currentRow = 5;
		while (addSubCount <= 8) {
			if (addOrSub) currentRow += addSubCount;
			else currentRow -= addSubCount;
		    addSubCount += 1;
		}
		Syste
	}
}
