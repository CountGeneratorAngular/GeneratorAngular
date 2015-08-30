package objectsQcm;

import java.util.ArrayList;
import java.util.List;

public class Entity {
	public String Name;
	public List<Attribute> attributes = new ArrayList<Attribute>();
	public List<Entity> entities = new ArrayList<Entity>();
	public Boolean isArray = false;
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Entity(String Name, Boolean isArray){
		this.Name = Name;
		this.isArray = isArray;
			
	}
	public void AddAttribute(Attribute att){
		this.attributes.add(att);	
	}
	public void RemoveAttribute(Attribute att){
		this.attributes.remove(att);	
	}
	public void RemoveAttributeByIndex(int i){
		this.attributes.remove(i);	
	}
	public List<Attribute> GetAttributes(){
		return this.attributes;
	}
	public Attribute GetAttribute(int i){
		return this.attributes.get(i);
	}	
	
	public void AddEntity(Entity ent){
		this.entities.add(ent);
		
	}
	
	public void RemoveEntity(Entity ent){
		this.entities.remove(ent);	
	}
	public void RemoveEntityByIndex(int i){
		this.entities.remove(i);	
	}
	public List<Entity> GetEntities(){
		return this.entities;
	}
	public Entity GetEntity(int i){
		return this.entities.get(i);
	}
	

}
