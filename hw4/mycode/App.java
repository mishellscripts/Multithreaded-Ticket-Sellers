import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class App {

	private static int i = 1;
	private static final int MAX = 150;
	private static final int SEC = 60;
	private static final int MEMORY = 100;
	
	private static AtomicInteger sec = new AtomicInteger(0);
	private volatile static int flagTA = 0; //terminate after 60sec
	public volatile static int okToPop = 0; //0 - not ok to pop, 1 - ok 
	
	private int prompt;

	public App(){
		
		DiskPageList disk = new DiskPageList();
		LinkedList<JobProcess> jobQ = disk.expand(generateASumulatedQueue());
		
		System.out.println("FIFO-1 LRU-2 LFU-3 MFU-4 Random-5");
		Scanner prompter = new Scanner(System.in);
		if(prompter.hasNextInt())
			prompt = prompter.nextInt();
		else
			return;
		
		if(prompt > 5 || prompt < 1)
			return;
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				// no need for synchronization since 
				// only one thread can access the data.
			
				if(sec.incrementAndGet() == 10)
					flagTA = 1;
				System.out.println(sec);

			}
			
		}, 1000,1000);
		
		while(flagTA != 1){
			// main job starts here
			
			
			
			
		}
		
		timer.cancel();
		/*
		for(int i = 0; i < jobQ.size(); i++){
			System.out.println(jobQ.get(i).getDiskIndex() + "   " 
						+ jobQ.get(i).getSize());
		}
		*/
		
		
		FreePageList freePage = new FreePageList(MEMORY);
		System.out.println(freePage);
		
	}
	/*
	 * Generate a queue of jobs 
	 * sort them based on arrival time
	 *
	 */
	public static LinkedList<JobProcess> generateASumulatedQueue(){
		LinkedList<JobProcess> q = new LinkedList<JobProcess>();
		for(int i = 0; i < MAX; i++){
			q.add(generateProcess());
		}

		JobProcess[] temp = q.toArray(new JobProcess[MAX]);
		q.clear();
		Arrays.sort(temp);
		
		q.addAll(Arrays.asList(temp));
		
		return q;
	}
	
	private static JobProcess generateProcess(){
		Random r = new Random(); 
		int arr[] = {5,11,17,31};
		
		int time = r.nextInt(5) + 1;
		
		int size = arr[r.nextInt(4)];
		
		int arrTime = r.nextInt(SEC);
		
		JobProcess p = new JobProcess("P" + i, size,
				arrTime, time);
		i++;
		return p;
		
	}
	
	
	public static void main(String[] args) {
		new App();
	}

}
