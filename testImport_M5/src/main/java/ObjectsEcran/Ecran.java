package ObjectsEcran;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

import ObjectsPage.Page;

/**
 * @author djerbienne
 *
 */
public class Ecran {

	Map<String, String> EcransValue = new HashMap<String, String>();
	Map<Integer, String> EcransTitle = new HashMap<Integer, String>();
	List<String> listFunctions = new ArrayList<String>();
	List<String> listAttributes = new ArrayList<String>();
	List<String> listEntities = new ArrayList<String>();
	List<Page> listPages = new ArrayList<Page>();
	Template pageTemplate ;
	String EcranName;
	String Entityctrl;
	/**
	 * Constructeur par défaut sans parametre
	 */
	public Ecran()
	{

	}
	/**
	 * Constructeur avec une variable le nom de l'ecran
	 * @param EcranName est le nom de l'ecran
	 */
	public Ecran(String EcranName)
	{
		this.EcranName=EcranName;
		
	}
	/**
	 * Identifier l'entité en cours
	 * @param ent est le nom de l'entité
	 */
	public void setEntity(String ent)
	{
		this.Entityctrl=ent;
	}
	/**
	 * Récuperer l'entité encore
	 * @return l'entité en cours
	 */
	public String getEntity(){
		return this.Entityctrl;
	}
	/**
	 * Récupérer le nom de l'ecran
	 * @return
	 */
	public String getEcranName() {
		return EcranName;
	}
	/**
	 * Ajouter une entité 
	 * @param ecranName est le nom de l'entité
	 */
	public void setEcranName(String ecranName) {
		EcranName = ecranName;
	}
	/**
	 * 
	 * @param ListOptionsTemplates est la liste des Templates choisies par l'utilisateur
	 */
	public void setList(ArrayList<String> ListOptionsTemplates)
	{
		//Parcourir la liste de Template
		for(int i = 0; i<ListOptionsTemplates.size();i++)
		{
			//Pour chaque Template appliquer la méthode setEcran
			this.setEcran(ListOptionsTemplates.get(i), "default");
		}
		
		
	}
	/**
	 * Ajouter dans la liste Map EcranValue le couple (nom de Template et valeurs)
	 * @param Template est le nom du Template
	 * @param Value est la valeur du Template
	 */
	public void setEcran(String Template, String Value)
	{
		EcransValue.put(Template, Value);
		EcransTitle.put(EcransTitle.size(),Template);
	}
	/**
	 * Récupérer l'ecran
	 * @param Ecran est le nom de l'ecran
	 * @return
	 */
	public String getEcran(String Ecran)
	{
		return EcransValue.get(Ecran);
	}
	
	/**
	 * Ajouter un Template
	 * @param t est le nom du Template
	 */
	public void setPage(Template t)
	{
		pageTemplate = t;		
	}
	/**
	 * Récupérer le Template
	 * @return
	 */
	public Template getPage()
	{		
		return pageTemplate;
	}
	/**
	 * Mise à jours des fonctions par page
	 */
	public void updateFunctions(){
		//Parcourir la liste des pages
		for(Page p:listPages)
		{   //Récupérer pour chaque page la liste des fonctions
			List<String> tempList = p.GetListFunction();
			//Parcourir la liste des Templates
			for(String str:tempList)
			{
				//Appliquer la méthode addFunction sur chaqu'un de Template
				this.addFunction(str);
			}			
		}		
	}
	/**
	 * Update la liste des Entitées
	 */
	public void updateEntities(){
		//Parcourir la liste des pages
		for(Page p:listPages)
		{
			//Récupérer pour chaque page l'entité associée 
			String tempString = p.getEntity();
			//pour chaqu'une des page Tester si le nom de l'entité n'existe pas dena la liste des entités
			if(!this.listEntities.contains(tempString))
				//Si c'est le cas donc on ajoute le nom de l'entité dans la liste des entitées.
				this.listEntities.add(tempString);
		}		
	}
	/**
	 * Update la liste des attributs
	 */
	public void updateAttributes(){
		//Parcourir la liste des pages
		for(Page p:listPages)
		{
			//Récupérer pour chaqu'une des pages la liste des attributs
			List<String> tempList = p.getListAttributes();
			//Parcourir la liste des attributs 
			for(String str:tempList)
			{
				//Appliquer la méthode addAttributes sur chaqu'un des attributs de la page
				this.addAttributes(str);
			}			
		}		
	}
	/**
	 * Ajouter une fonction dans la liste des fonctions
	 * @param function est le nom de la fonction
	 */
	public void addFunction(String function){
		//Si la liste des fonctions ne contient pas la fonction donner en parametre
		if(!this.listFunctions.contains(function))
		{
			//Dans ce cas le nom de la fonction sera ajouter dans la liste des fonctions
			this.listFunctions.add(function);
		}		
	}
	/**
	 * Ajouter un attributs dans la liste des attributs
	 * @param attr est le nom de l'attributs
	 */
	public void addAttributes(String attr){
		//Tester si la listes des attributs ne contient pas l'attribut passer en parametre
		if(!this.listAttributes.contains(attr))
		{
			//Si le teste est positif dans ce cas le nom d'attribut sera ajouter à la liste des ttributs
			this.listAttributes.add(attr);
		}		
	}
	/**
	 *Récupérer la liste des attributs
	 * @return
	 */
	public List<String> getListAttributes(){
		return this.listAttributes;
	}
	/**
	 * Récupérer la liste des entités
	 * @return
	 */
	public List<String> getListEntities(){
		return this.listEntities;
	}
	/**
	 * Récupérer la liste des ecrans sous forme d'un String
	 * @return
	 */
	public String getListEcran(){
		String test = "";
		for(int i = 0; i<EcransTitle.size();i++)
		{
			test +="-" +EcransTitle.get(i);
		}
		return test;	
	}
	
	/**
	 * Afficher sur console la liste des fonctions
	 */
	public void printFunctions(){
		System.out.println(this.listFunctions);
	}
	/**
	 * Générer les fichier des controlleurs et des factories
	 * @param context est le contexte
	 * @param tCtrl est le template controlleur
	 * @param tFact est le template factory
	 */
	public void generateGlobal(Context context, Template tCtrl, Template tFact){
		try{
		StringWriter writer = new StringWriter();
		FileWriter fw;
		context.put("listFunction",this.listFunctions);
		tCtrl.merge(context, writer);
		fw = new FileWriter(System.getProperty("user.dir")+"/AngularNew/Src/Controller/"+this.EcranName+"Controller.js");
		fw.write(writer.toString());
		fw.close();
		writer = new StringWriter();
		tFact.merge(context, writer);
		fw = new FileWriter(System.getProperty("user.dir")+"/AngularNew/Src/Factory/"+this.EcranName+"Factory.js");
		fw.write(writer.toString());
		fw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	/**
	 * Ajouter une page à la liste des pages s'il n'exsite pas
	 * @param p est la page
	 */
	public void addPage(Page p){
		if(!this.listPages.contains(p))
		{
			this.listPages.add(p);
		}
	}
	
	/**
	 * Affecter à la chaine "Pages" la liste des pages comme valeur
	 * @param c est une extension de l'objet VelocityContext
	 */
	public void setPageList(VelocityContext c){
		c.put("Pages",this.listPages);
	}
	/**
	 * Récupérer la liste des pages
	 * @return
	 */
	public List<Page> getPageList(){
		return this.listPages;
	}
	
	/**
	 * 
	 * @param c est une extension de l'objet VelocityContext
	 */
	public void setContext(VelocityContext c)
	{
		for(int i = 0; i<EcransTitle.size();i++)
		{
			c.put(EcransTitle.get(i),EcransValue.get(EcransTitle.get(i)));
			System.out.println(EcransTitle.get(i));
		}				
	}
	
}
