

public class SellerH extends Seller {

	private Object lock;
	public SellerH(Seat[][] s, String sellerID, Object lk) {
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
			// Case for Seller H
			Seat seat = null;

			synchronized(lock) {	
				find_seat:
					for (int i = 0; i < seating.length; i++) {
						for (int j = 0; j < seating[0].length; j++) {
							if (seating[i][j].isSeatEmpty()) {
								// Assign seat to customer
								// Seat number = (Row x 10) + (Col + 1)
								int seatNum = (i*10)+j+1;
								seat = new Seat(seatNum);
								super.assignSeat(customer, seat, i, j);
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
			printMsg(customer, seat);
			
			customers.remove();
		}
	}

}
