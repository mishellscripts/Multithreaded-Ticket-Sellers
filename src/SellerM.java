import java.util.Random;

public class SellerM extends Seller{
	int serviceTime;
	Random r = new Random();
	
	public SellerM() {
		// Seller H takes 1 or 2 minutes to complete a ticket sale
		serviceTime = r.nextInt(4) + 2;
	}
}
