import java.util.LinkedList;

public class DiskPageList extends LinkedList<MemoryFrame>{
	
	private int space;
	
	public LinkedList<JobProcess> expand(LinkedList<JobProcess> q){
		for(JobProcess p : q){
			setP(p);
		}
		return q;
		
	}
	
	//expansion
	private void setP(JobProcess p){
		p.setDiskIndex(space);
		for(int i = 0; i < p.getSize(); i++){
			this.add(new MemoryFrame(p.getName(),space));
			space++;
		}
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		
		for(MemoryFrame f : this){
			builder.append(f.getPName() + ": \tindex: " + f.getIndex());
			builder.append("\n");
			
		}
		return builder.toString();
	}
	
	
}
