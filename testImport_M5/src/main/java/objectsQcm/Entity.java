package objectsQcm;

import java.util.ArrayList;

public class Entity {
	public String Name;
	public ArrayList<Attribute> attributes = new ArrayList<Attribute>();
	public ArrayList<Entity> entities = new ArrayList<Entity>();;
	public Boolean isArray = false;
	
	/**
	 * Constructeur avec deux variables
	 * @param Name est le nom de l'entité
	 * @param isArray est un array ou pas
	 */
	public Entity(String Name, Boolean isArray){
		this.Name = Name;
		this.isArray = isArray;			
	}
	
	/**
	 * Récupérer le nom de l'entité
	 * @return
	 */
	public String getName() {
		return Name;
	}
	
	/**
	 * Ajouter le nom de l'entité
	 * @param name est le nom de l'entité
	 */
	public void setName(String name) {
		Name = name;
	}
		
	/**
	 * Ajouter un attributs
	 * @param att est le nom de l'attribut
	 */
	public void AddAttribute(Attribute att){
		this.attributes.add(att);	
	}
	
	/**
	 * Supprimer un attribut
	 * @param att est le nom de l'attribut
	 */
	public void RemoveAttribute(Attribute att){
		this.attributes.remove(att);	
	}
	
	/**
	 * Supprimer un attribut par index
	 * @param i est l'indice de l'attribut
	 */
	public void RemoveAttributeByIndex(int i){
		this.attributes.remove(i);	
	}
	
	/**
	 * Récupérer la liste des attributs
	 * @return
	 */
	public ArrayList<Attribute> GetAttributes(){
		return this.attributes;
	}
	
	/**
	 * Récupérer un attribut
	 * @param i est l'indice de l'attribut
	 * @return
	 */
	public Attribute GetAttribute(int i){
		return this.attributes.get(i);
	}	
	
	/**
	 * Ajouter une entité
	 * @param ent est une entité
	 */
	public void AddEntity(Entity ent){
		this.entities.add(ent);		
	}
	
	/**
	 * Supprimer une entité
	 * @param ent est une entité
	 */
	public void RemoveEntity(Entity ent){
		this.entities.remove(ent);	
	}
	
	/**
	 * Supprimer une entité par index
	 * @param i est l'indice de l'entité
	 */
	public void RemoveEntityByIndex(int i){
		this.entities.remove(i);	
	}
	
	/**
	 * Récupérer la liste des entités
	 * @return
	 */
	public ArrayList<Entity> GetEntities(){
		return this.entities;
	}
	
	/**
	 * Récupérer une entité par indice
	 * @param i est l'indice de l'entité
	 * @return
	 */
	public Entity GetEntity(int i){
		return this.entities.get(i);
	}
	

}
