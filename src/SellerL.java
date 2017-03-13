import java.util.Random;

public class SellerL extends Seller{
private int serviceTime;
private Random r;
	
public SellerL(String type,Seat[][] s) {
		// Seller H takes 1 or 2 minutes to complete a ticket sale
	super(type,s);
		serviceTime = r.nextInt(7) + 4;
		r = new Random();
	}
}
