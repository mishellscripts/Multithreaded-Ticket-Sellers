import java.util.Random;

public class SellerH extends Seller {
	private int serviceTime;
	//Seat[][] seating;

	public SellerH(Seat[][] s) {
		// Seller H takes 1 or 2 minutes to complete a ticket sale
		super(s);
		serviceTime = r.nextInt(2) + 1;
	}
	 
}
