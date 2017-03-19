public class Simulation {
	public void wakeupSellers() {

	}
	public static void main(String[] args) {

		//number of customers per seller per hour
		int N = 10; //change to command line argument later?

		final Object lock = new Object();

		int maxRows = 10;
		int maxCols = 10;
		Seat[][] seating = createSeating(maxRows, maxCols);

		//create 10 threads representing 10 sellers
		Seller[] allSellers = new Seller[10];
		for (int numSeller = 0; numSeller < 10; numSeller++)
		{
			if (numSeller == 0) allSellers[numSeller] = new SellerH(seating, lock);
			else if (numSeller >= 1 && numSeller < 4) allSellers[numSeller] = new SellerM(seating, lock);
			else if (numSeller >= 4 && numSeller < 10) allSellers[numSeller] = new SellerL(seating, lock);
		}

		//add N customers for each seller for each hour
		//initially add N customers for each seller's queue
		allSellers = addNewCustomers(allSellers, N);

		//wake up all seller threads, so they can run in "parallel"
		//lock.notifyAll(); //use notifyAll for broadcast
//		for(int numSellers = 0; numSellers < allSellers.length; numSellers++)
//		{
//			Thread currentThread = new Thread(allSellers[numSellers]);
//			currentThread.start();
//		}

		//print the following with the current time
		//- customer added to the queue
		//- customer is attended (given a seat or told there are no seats)
		//- customer gets a ticket and goes to seat
		//- print seating chart everytime a ticket is sold

		//wait for all sellers to finish
		for (int numSellers = 0; numSellers < allSellers.length; numSellers++)
		{
			Thread currentThread = new Thread(allSellers[numSellers]);
			currentThread.start();
			try {
				currentThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		synchronized(lock) {
			printSeating(seating, maxRows, maxCols);
		}



		//seller needs to take in parameters or have some ref to
		//2D array, time?, universal lock
	}

	/**
	 * Create a seating chart and label with seat numbers
	 * @param maxRows: max number of rows for the chart
	 * @param maxCols max number of columns for the chart
	 * @return seating chart with the given size and fully labeled
	 */
	public static Seat[][] createSeating(int maxRows, int maxCols)
	{
		//create 10x10 seating and label with seat numbers
		Seat[][] seating = new Seat[maxRows][maxCols]; 
		int numSeat = 1;
		for (int row = 0; row < maxRows; row++)
		{
			for (int column = 0; column < maxCols; column++)
			{
				seating[row][column] = new Seat(numSeat);
				numSeat++;
			}
		}
		return seating;
	}

	/**
	 * Add the given number of customers for each seller's queue
	 * @param allSellers: array containing all the sellers
	 * @param numAdd: number of customers to add to each seller
	 * @return updated array with the new customers added to each queue
	 */
	public static Seller[] addNewCustomers(Seller[] allSellers, int numAdd)
	{
		for (int numSeller = 0; numSeller < allSellers.length; numSeller++)
		{
			for (int count = 0; count < numAdd; count++)
			{
				Customer c = new Customer(numSeller);
				allSellers[numSeller].addCustomer(c);
			}
			//allSellers[numSeller].sortQueue();
		}
		return allSellers;
	}

	/**
	 * Print the current seating chart
	 * @param seating: current seating chart
	 * @param maxRows: max number of rows for the chart
	 * @param maxCols: max number of columns for the chart
	 */
	public static void printSeating(Seat[][] seating, int maxRows, int maxCols)
	{
		System.out.printf("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		for (int row = 0; row < maxRows; row++)
		{
			for (int col = 0; col < maxCols; col++)
			{
				if (seating[row][col].isSeatEmpty()) System.out.printf("%5s ", "O");
				else System.out.printf("%5s ", "X");
			}
			System.out.println();
		}
	}

}
