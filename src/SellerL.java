import java.util.Random;

public class SellerL extends Seller{
	int serviceTime;
	Random r = new Random();
	
	public SellerL(Seat[][] s) {
		// Seller H takes 1 or 2 minutes to complete a ticket sale
		serviceTime = r.nextInt(7) + 4;
	}
}
