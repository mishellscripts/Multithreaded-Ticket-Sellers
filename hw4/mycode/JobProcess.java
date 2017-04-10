import java.util.LinkedList;

public class JobProcess implements Comparable<JobProcess>{
	
	private String name;
	private int size;
	private int arrTime;
	private int serTime;
	private LinkedList<MemoryFrame> frames;
	private int currentI;
	private int diskIndex;
	
	public JobProcess(String name, int size, int arrtime, int sertime){
		this.name = name;
		this.size = size;
		this.arrTime = arrtime;
		this.serTime = sertime;
		frames = new LinkedList<>();
	}
	
	// for setting the index of the diskList of this job
	public void setDiskIndex(int i){
		this.diskIndex = i;
		this.currentI = i;
	}
	
	public void setCurrentI(int i){
		this.currentI = i;
	}
	
	
	public int getDiskIndex(){
		return this.diskIndex;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getSize(){
		return this.size;
	}
	
	public int getArr(){
		return this.arrTime;
	}
	
	public int getSer(){
		return this.serTime;
	}
	
	public LinkedList<MemoryFrame> getFrames(){
		return this.frames;
	}
	
	public void addFrame(MemoryFrame f){
		this.frames.add(f);
	}

	@Override
	public String toString(){
		return this.name + " Size:" + this.size + " Arrival:" + 
				this.arrTime + " Service:" + this.serTime + "\n";
		
	}
	
	@Override
	public int compareTo(JobProcess o) {
		
		int diff = this.arrTime - o.arrTime;
		
		return diff;
	}
	
	
}
