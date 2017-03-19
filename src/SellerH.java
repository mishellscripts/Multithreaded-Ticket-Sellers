import java.util.Random;

public class SellerH extends Seller {

	private Object lock;
	public SellerH(Seat[][] s, Object lk) {
		// Seller H takes 1 or 2 minutes to complete a ticket sale
		super(s, r.nextInt(2) + 1, lk);
		lock = lk;
	}

<<<<<<< HEAD
//	public void sell() throws InterruptedException {
//		while (!customers.isEmpty()) {						
//			//Object lock = new Object();
//
//			while (customers.isEmpty()) return;
//			// Get customer in queue that is ready
//			Customer customer = customers.peek();
//
//			// Find seat for the customer
//			// Case for Seller H
//			boolean found = false;
//
//			synchronized(lock) {	
//				find_seat:
//					for (int i = 0; i < seating.length; i++) {
//						for (int j = 0; j < seating[0].length; j++) {
//							if (seating[i][j].isSeatEmpty()) {
//								// Assign seat to customer
//								// Seat number = (Row x 10) + (Col + 1)
//								int seatNum = (i*10)+j+1;
//								Seat seat = new Seat(seatNum);
//								seat.assignSeat(customer);
//								seating[i][j] = seat;
//								found = true;
//								break find_seat;
//							}
//						}
//					}
//			lock.notifyAll();
//			}
//
//			if (!found) System.out.println("H - Sorry, the concert is sold out!");
//
//
//			customers.remove();
//		}
//	}
=======
	public void sell() {
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
			lock.notifyAll();
			}

			if (!found) System.out.println("H - Sorry, the concert is sold out!");


			customers.remove();
		}
	}
>>>>>>> stash

}
