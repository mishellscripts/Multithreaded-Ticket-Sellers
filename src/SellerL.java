import java.util.Random;

public class SellerL {
	int serviceTime;
	Random r = new Random();
	
	public SellerL() {
		// Seller H takes 1 or 2 minutes to complete a ticket sale
		serviceTime = r.nextInt(7) + 4;
	}
}
