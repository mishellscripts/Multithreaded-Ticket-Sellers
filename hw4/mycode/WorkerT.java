import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkerT implements Runnable{

	private JobProcess jobProcess;
	private FreePageList freePageL;
	private DiskPageList diskPageL;
	private Object mutex;
	private AtomicInteger time;
	private int duration;
	private int jobSize;
	private int haventPOP = 1;
		
	private int cIndex;
	
	private static Random r = new Random();
	private static int[] deltaI = {-1,0,1}; //for locality of reference
	
	public WorkerT(JobProcess jb, FreePageList flist, Object lock, AtomicInteger num){
		this.jobProcess = jb;
		this.duration = jb.getSer() * 1000;
		this.mutex = lock;
		this.freePageL = flist;
		this.time = num;
		this.jobSize = jb.getSize();
	}
	
	@Override
	public void run() {
		while(time.get() <= 60  && App.flagTA == 0){
			
			//when it is finished
			if(duration <= 0){
				//System.out.println("BREAK");
				if(this.jobProcess.getFrames().size() != jobSize + 1){
					App.okToPop = 1;
					//System.out.println("ENTERRRRRRRRRRRRRR");
					haventPOP = 0;
				}
				
				removeTheContentFromM();

				break;
				
			}
			
			//implement when to write to memory
			//put all disk memory to physical memory
			while(jobSize + 1 > this.jobProcess.getFrames().size()){
				writeToMemory();
				if(App.flagTA == 1){
					break;
				}
			}
			
			//after it has put all the disk data to physical memory
			// it will flag the main thread to pop the job
			if(jobSize + 1 == this.jobProcess.getFrames().size() && haventPOP == 1){
				App.okToPop = 1;
				//System.out.println("ENTERRRRRRRRRRRRRR");
				haventPOP = 0;
			}
			
			readFromMemory();
			
		}
	}
	
	public void writeToMemory(){
		// first check if jobProcess has zero index
		int nextIndex = -1;
		if(this.cIndex == 0){
			//this is for the start of the execution
			synchronized (this.mutex) {
				if(this.freePageL.hasenoughSpace()){
					this.freePageL.doAt(this.jobProcess, 0);
					this.jobProcess.addFrame(new MemoryFrame(jobProcess.getName(), 0));
					cIndex--;

					doSomething(0);
					//clean up freePageList zeroth index
					this.freePageL.clean(0);
					// make sure that it wont get the zeroth index again in fpl or -1
					while(!withinBound(nextIndex)){	
						nextIndex = fixBound(GenerateLoR(0));
					}
					// loop until nextindex is not zero or the nextindex in the memory is empty
					while(this.freePageL.get(nextIndex).getIndex() == 0 || !this.freePageL.get(nextIndex).isEmp()){ 
						nextIndex = nextIndex + GenerateLoR(nextIndex);
						
						while(!withinBound(nextIndex)){	
							nextIndex = fixBound(nextIndex);
						}
						
					}
					this.freePageL.doAt(this.jobProcess, nextIndex);
					this.jobProcess.addFrame(this.freePageL.get(nextIndex)); //not sure 

					doSomething(nextIndex);
					
				}
				
			}
		}else{		
			
			//get the job list current memoryframe index
			nextIndex = fixBound(GenerateLoR(this.jobProcess.getFrames().get(cIndex).getIndex())); //should still be within locality of ref
		
			synchronized(this.mutex){
				
				if(this.freePageL.getFreeSpace() > 0){
					while(!this.freePageL.get(nextIndex).isEmp()){ 
						nextIndex = nextIndex + GenerateLoR(this.jobProcess.getFrames().get(cIndex).getIndex());

						while(!withinBound(nextIndex)){	
							nextIndex = fixBound(nextIndex);
						}

						
					}
					// if memory frame is not occupied, do the following
					
					this.freePageL.doAt(this.jobProcess, nextIndex);
					if(this.jobProcess.getFrames().size() < jobSize+1){
						this.jobProcess.addFrame(this.freePageL.get(nextIndex)); //this is the problem. <-----
						//System.out.println("ADD");
					}else{
						this.jobProcess.getFrames().set(cIndex, this.freePageL.get(nextIndex));
						//System.out.println("SET");
					}
					doSomething(nextIndex);	
					
					 
				}else{
					// swapping algorithm
					//doesnt need nextIndex but cindex for sure
					int indexForSwap = -1;
					
					// no need to synchronize this
					if(App.prompt == 5){
						//random pick
						indexForSwap = r.nextInt(App.MEMORY);
					}
					
					synchronized(this.mutex){
						this.freePageL.doAt(this.jobProcess, indexForSwap);
						this.jobProcess.getFrames().set(cIndex ,this.freePageL.get(indexForSwap));
						

						doSomething(indexForSwap);
					}
				}
				
			}
		}
			
		
	}
	// compare the each jobs frame to physical frame
	// to see if it matches or not
	// if not, call writeToMemory
	public void readFromMemory(){
		
		
		if(App.prompt == 5){
			if(!this.jobProcess.getFrames().get(cIndex).getPName().equals(this.jobProcess.getName())){
				writeToMemory();
			}else{
				doSomething();
			}				
			
			

		}
	}
	
	// might reimplement this
	public void removeTheContentFromM(){
		synchronized(this.mutex){
		
			int indexForFrame;
			for( int i = 1 ;i < this.jobProcess.getFrames().size(); i++){
					// if this jobProcess queue element name is equal to this process name
					// remove it from memory
				if(this.jobProcess.getFrames().get(i).getPName().equals(this.jobProcess.getName())){
					indexForFrame = this.jobProcess.getFrames().get(i).getIndex();
					this.freePageL.clean(indexForFrame);
					//System.out.println("YOYOYOYOYOYOYOY");
				}
			}
		}
	}
	
	private void writeRecord(int pageReferenced){
		System.out.println("---------Time: " + time.get() + "--------------------------");
		//System.out.println("Process: " + this.jobProcess.getName() + " index: " + cIndex + " jB size: " + this.jobProcess.getFrames().size()+ " " + this.jobSize + " page refer: " + pageReferenced);
		System.out.println("Process: " + this.jobProcess.getName()  +  " page refer: " + pageReferenced);
		//System.out.println("Is page in memory: ");
		System.out.println(this.freePageL);
		
	}
	
	//do something for 100 msec
	public void doSomething(int pageReferenced){
		try {
			
			if(cIndex == jobSize)
				this.cIndex = 1;	//circle back to 1
			else
				this.cIndex++;
			
			writeRecord(pageReferenced);
			
			duration = duration - 100;
			Thread.sleep(100);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doSomething(){
		try {
			
			if(cIndex == jobSize)
				this.cIndex = 1;	//circle back to 1
			else
				this.cIndex++;
			
			
			duration = duration - 100;
			Thread.sleep(100);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private boolean withinBound(int index){
		if(index <= 99 && index >= 1 ){
			return true;
		}
		
		return false;
	}
	
	private int fixBound(int index){
		
		while(index < 1){
			index = index + 100;
		}
		
		while(index > 99){
			index = index - 99;
		}

		return index;
	}
	
	// generate the locality of reference
	private int GenerateLoR(int index){
		int pageReference;
		int k = r.nextInt(2);
		int j;
		int first = r.nextInt(10);
		if(first >= 0 && first <= 6) //70 percent
			pageReference = index + deltaI[r.nextInt(3)]; //0,1,2 index
		else{
			j = r.nextInt(8) + 2;
			if(k == 0)
				pageReference = index + r.nextInt(j+1);
			else
				pageReference = index - r.nextInt(j+1);

		}
		return pageReference;
		
	}

}










