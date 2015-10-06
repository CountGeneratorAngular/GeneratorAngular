package objectsQcm;

public class Attribute {

	public String Name;
	public String Type;
	public String Value;
	public Boolean isArray = false;
	
	/**
	 * Constructeur avec 3 varibles
	 * @param Name est le nom de l'attribut
	 * @param Type est le type d'attribut
	 * @param isArray est un array ou pas
	 */
	public Attribute(String Name, String Type, Boolean isArray)
	{
		this.Name = Name;
		this.Type = Type;
		this.isArray = isArray;	
	}

	/**
	 * Récupérer le nom de l'attribut
	 * @return
	 */
	public String getName() {
		return Name;
	}

	/**
	 * Ajouter un attribut
	 * @param name est le nom de le'attribut
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * Récupérer le type de l'attribut
	 * @return
	 */
	public String getType() {
		return Type;
	}

	/**
	 * Set Type
	 * @param type est le type des attributs
	 */
	public void setType(String type) {
		Type = type;
	}
	
	
	
}
