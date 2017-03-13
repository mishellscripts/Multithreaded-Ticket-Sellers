public class Simulation {
	
	public static void main(String[] args) {
		
		//number of customers per seller per hour
		int N = 10; //change to command line argument later?
		
		
		//create 10x10 seating and label with seat numbers
		Seat[][] seating = new Seat[10][10]; 
		int numSeat = 1;
		for (int row = 0; row < 10; row++)
		{
			for (int column = 0; column < 10; column++)
			{
				seating[row][column] = new Seat(numSeat);
				numSeat++;
			}
		}
		
		
		//create 10 threads representing 10 sellers
		Seller[] allSellers = new Seller[10];
		for (int numSeller = 0; numSeller < 10; numSeller++)
		{
			if (numSeller == 0) allSellers[numSeller] = new SellerH();
			else if (numSeller >= 1 && numSeller < 4) allSellers[numSeller] = new SellerM();
			else if (numSeller >= 4 && numSeller < 10) allSellers[numSeller] = new SellerL();
		}
		
		
		//add N customers for each seller for each hour
		//initially add N customers for each seller's queue
		
		
		
		//wake up all seller threads, so they can run in "parallel"
		//use notifyAll for broadcast
		
		
		
		
		//print the following
		//-
	}
	
	
	
}
