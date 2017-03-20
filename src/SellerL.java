import java.util.Random;

public class SellerL extends Seller{

	private Object lock;
	public SellerL(Seat[][] s, String sellerID, Object lk) {
		// Seller H takes 1 or 2 minutes to complete a ticket sale
		super(s, r.nextInt(2) + 1, sellerID, lk);
		lock = lk;

	}

	public void sell() {
		while (!customers.isEmpty()) {						
			//Object lock = new Object();

			if (customers.isEmpty()) return;
			// Get customer in queue that is ready
			Customer customer = customers.peek();

			// Find seat for the customer
			// Case for Seller L
			Seat seat = null;

			synchronized(lock) {
				find_seat:
					for (int i = seating.length-1; i >= 0; i--) {
						for (int j = 0; j < seating[0].length; j++) {
							if (seating[i][j].isSeatEmpty()) {
								// Assign seat to customer
								// Seat number = (Row x 10) + (Col + 1)
								int seatNum = (i*10)+j+1;
								seat = new Seat(seatNum);
								if(ticketNum < 10)
									customer.setTicket(sellerID + "0" + ticketNum);
								else
									customer.setTicket(sellerID + ticketNum);
								ticketNum++;
								seat.assignSeat(customer);
								seating[i][j] = seat;
								break find_seat;
							}
						}
					}
			//lock.notifyAll();
			}
			try {
				Thread.sleep(serviceTime * 100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (seat == null) System.out.println(sellerID + " - Sorry, the concert is sold out!");
			else System.out.println(sellerID + " - Success! Your seat is " + seat.getSeatNumber());

			customers.remove();
		}
	}
}
