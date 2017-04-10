
public class MemoryFrame {
	private volatile String pname;
	private int index;
	private static final int SIZE = 1; //MB
		
	public MemoryFrame(String pname, int index){
		this.pname = pname;
		this.index = index;
	}
	
	public String getPName(){
		return this.pname;
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public void setFrame(String pname, int index){
		this.pname = pname;
		this.index = index;
		
	}
	
	

	public boolean isEmp(){
		return this.pname.equals(".");
	}
	public String toString(){
		return this.pname + " at index: " + this.index;
	}
	
	
	
}
