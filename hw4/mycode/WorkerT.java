import java.util.Random;

import java.util.concurrent.atomic.AtomicInteger;

public class WorkerT implements Runnable{

	private JobProcess jobProcess;
	private FreePageList freePageL;
	private DiskPageList diskPageL;
	private Object mutex;
	private int time;
	private int duration;
	private int jobSize;
	
	private int flag;	//will implement timer to check duration
	
	private int cIndex;
	
	private static Random r = new Random();
	private static int[] deltaI = {-1,0,1}; //for locality of reference
	
	public WorkerT(JobProcess jb, FreePageList flist, DiskPageList dlist,Object lock, AtomicInteger num){
		this.jobProcess = jb;
		this.duration = jb.getSer();
		this.mutex = lock;
		this.freePageL = flist;
		this.diskPageL = dlist;
		this.time = num.get();
		this.jobSize = jb.getSize();
	}
	
	@Override
	public void run() {
		while(time <= 60 && flag == 0){
			
			//implement when to write to memory
			//put all disk memory to physical memory
			while(jobSize + 1 != this.jobProcess.getFrames().size()){
				writeToMemory();
			}
			
			//after it has put all the disk data to physical memory
			// it will flag the main thread to pop the job
			App.okToPop = 1;
		}
	}
	
	public void writeToMemory(){
		// first check if jobProcess has zero index
		int nextIndex;
		if(this.cIndex == 0){
			//this is for the start of the execution
			synchronized (this.mutex) {
				if(this.freePageL.hasenoughSpace()){
					this.freePageL.doAt(this.jobProcess, 0);
					this.jobProcess.addFrame(new MemoryFrame(jobProcess.getName(), 0));
					doSomething();
					
					//clean up freePageList zeroth index
					this.freePageL.clean(0);
					// make sure that it wont get the zeroth index again in fpl
					nextIndex = GenerateLoR(0);
					while(nextIndex == 0){
						nextIndex = GenerateLoR(0);
					}
					this.freePageL.doAt(this.jobProcess, nextIndex);
					this.jobProcess.addFrame(new MemoryFrame(jobProcess.getName(), nextIndex));
					doSomething();
					
					this.cIndex++; 
				}
				
			}
		}else{					//get the job list current memoryframe index
			nextIndex = GenerateLoR(this.jobProcess.getFrames().get(cIndex).getIndex());
			
			synchronized(this.mutex){
				//IMPLEMENT to check if it goes outofbound
				// if it is memory frame is not occupied, do the following
				if(this.freePageL.get(nextIndex).isEmp()){
					this.freePageL.doAt(this.jobProcess, nextIndex);
					this.jobProcess.addFrame(new MemoryFrame(jobProcess.getName(), nextIndex));
					doSomething();	
					
					if(cIndex == jobSize)
						this.cIndex = 1;	//circle back to 1
					else
						this.cIndex++; 
				}else{
					//use algorithm
				}
			}
		}
			
		
	}
	// compare the each jobs frame to physical frame
	// to see if it matches or not
	// if not, call writeToMemory
	public void readFromMemory(){
		
	}
	
	//do something for 100 msec
	public void doSomething(){
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	// generate the locality of reference
	private int GenerateLoR(int index){
		int pageReference;
		int j;
		int first = r.nextInt(10);
		if(first >= 0 && first <= 6) //70 percent
			pageReference = index + deltaI[r.nextInt(3)]; //0,1,2 index
		else{
			j = r.nextInt(8) + 2;
			pageReference = r.nextInt(j+1);
		}
		return pageReference;
		
	}

}










