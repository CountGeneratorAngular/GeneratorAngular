package objectsQcm;

public class entityVelocity {
	
	public Object[] attributes;
	public String[] parent;
	public String name;
	
	public entityVelocity(Object[] a, String[] p, String n){
		this.attributes = a;
		this.parent = p;
		this.name = n;
		
		
	}
	public Object[] getAtt(){
		
		return attributes;
	}
	public String[] getParent(){
		
		return parent;
	}
	public String getName(){
		
		return name;
	}
	

}
