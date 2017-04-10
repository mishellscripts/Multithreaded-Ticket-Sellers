import java.util.LinkedList;

public class FreePageList extends LinkedList<MemoryFrame>{
	private volatile int freeSpace;
		
	public FreePageList(int free){
		
		this.freeSpace = free;
		initializeMF();
	
	}
	
	private void initializeMF(){
		for(int i = 0; i < this.freeSpace; i++)
			this.add(new MemoryFrame(".", i));
	}
	
	//
	public void doAt(JobProcess p, int index){
		this.get(index).setFrame(p.getName(), index);
		this.freeSpace--;
	}
	
	//clean up the finished frames
	public void clean(int index){
		this.get(index).setFrame(".", index);
		this.freeSpace++;
		
	}
	
	
	// if there is enough free space
	public boolean hasenoughSpace(){
		return (this.freeSpace >= 4);
	}
	
	public int getFreeSpace(){
		return this.freeSpace;
	}
	
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < App.MEMORY; i++){
			builder.append(this.get(i).getPName());
		}
		/*
		for(int i = 0; i < this.freeSpace; i++){
			builder.append(this.get(i) + "\n");
		}
		*/
		return builder.toString();
	}
}
