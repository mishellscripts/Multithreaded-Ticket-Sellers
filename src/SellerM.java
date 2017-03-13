import java.util.Random;

public class SellerM extends Seller{
	int serviceTime;
	Random r = new Random();
	
	public SellerM(String type, Seat[][] s) {
		// Seller H takes 1 or 2 minutes to complete a ticket sale
		super(type,s);
		serviceTime = r.nextInt(4) + 2;
	}
}
