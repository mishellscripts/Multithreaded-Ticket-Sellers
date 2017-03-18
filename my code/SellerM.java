import java.util.Random;

public class SellerM extends Seller{

	
	public SellerM(Seat[][] s) {
		// Seller H takes 1 or 2 minutes to complete a ticket sale
		super(s, r.nextInt(4) + 2);
	}
}
