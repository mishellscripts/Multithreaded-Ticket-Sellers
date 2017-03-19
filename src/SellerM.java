import java.util.Random;

public class SellerM extends Seller{
	private int serviceTime;
	private Object lock;

	public SellerM(Seat[][] s, Object lk) {
		// Seller H takes 1 or 2 minutes to complete a ticket sale
		super(s, r.nextInt(4) + 2, lk);
		lock = lk;
	}

	public void sell() {
		while (!customers.isEmpty()) {						
			//Object lock = new Object();

			while (customers.isEmpty()) return;
			// Get customer in queue that is ready
			Customer customer = customers.peek();

			// Find seat for the customer
			// Case for Seller M
			boolean flag = true;
			int counter = 1;

			Seat seat = null;
			
			synchronized(lock) {
				find_seat:
					for(int i = 5; i >= 0 && i < seating.length;) {
						for (int j = 0; j < seating[0].length; j++) {
							if (seating[i][j].isSeatEmpty()) {
								// Assign seat to customer
								// Seat number = (Row x 10) + (Col + 1)
								int seatNum = (i*10)+j+1;
								seat = new Seat(seatNum);
								seat.assignSeat(customer);
								seating[i][j] = seat;
								break find_seat;
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
			lock.notifyAll();

			}

			if (seat == null) System.out.println("M - Sorry, the concert is sold out!");
			else System.out.println("M - Success! Your seat is " + seat.getSeatNumber());

			customers.remove();
		}
	}
}
