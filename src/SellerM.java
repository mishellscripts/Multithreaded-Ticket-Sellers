import java.util.Random;

public class SellerM extends Seller{
<<<<<<< HEAD

	
=======
	//Seat[][] seating;
	private int serviceTime;

>>>>>>> 00231be48498f770b6f7fe9ec6fb40e20c15dc17
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
				
				find_seat:
					
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
