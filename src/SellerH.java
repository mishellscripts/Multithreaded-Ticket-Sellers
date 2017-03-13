import java.util.Random;

public class SellerH extends Seller {
	int serviceTime;
	Random r = new Random();
	Seat[][] seating;

	
	public SellerH(String type, Seat[][] s) {
	public SellerH(Seat[][] s) {
		// Seller H takes 1 or 2 minutes to complete a ticket sale
		super(type,s);
		serviceTime = r.nextInt(2) + 1;
	}
	 
}
