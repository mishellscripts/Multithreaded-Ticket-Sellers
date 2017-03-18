import java.util.Random;

public class SellerH extends Seller {

	public SellerH(Seat[][] s) {
		// Seller H takes 1 or 2 minutes to complete a ticket sale
		super(s, r.nextInt(2) + 1);
	}
	 
}
