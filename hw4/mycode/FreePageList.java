import java.util.LinkedList;

public class FreePageList extends LinkedList<MemoryFrame>{
	private int freeSpace;
		
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
	}
	
	
	// if there is enough free space
	public boolean hasenoughSpace(){
		return (this.freeSpace >= 4);
	}
	
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < this.freeSpace; i++){
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
