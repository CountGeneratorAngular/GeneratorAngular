package objectsQcm;

public class entityVelocity {
	
	public Object[] attributes;
	public String[] parent;
	public String name;
	
	/** Constructeur avec troix variables
	 * @param a est un tableau d'objet
	 * @param p est la table des parent
	 * @param n est le nom de l'entityVelocity
	 */
	public entityVelocity(Object[] a, String[] p, String n){
		this.attributes = a;
		this.parent = p;
		this.name = n;		
	}
	
	/**
	 * Récupérer le tableau d'attributs
	 * @return
	 */
	public Object[] getAtt(){		
		return attributes;
	}
	
	/**
	 * Récupérer le tableau des parents
	 * @return
	 */
	public String[] getParent(){		
		return parent;
	}
	
	/**
	 * Récupérer le nom de l'entityVelocity
	 * @return
	 */
	public String getName(){		
		return name;
	}
	

}
