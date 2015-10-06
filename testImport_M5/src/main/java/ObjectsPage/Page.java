package ObjectsPage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;


/**
 * @author djerbienne
 *
 */
public class Page {
	String PageName;
	Template PageTemplate;
	String TemplateName;
	List<String> ListFunctions = new ArrayList<String>();
	List<String>  ListAttributs = new ArrayList<String>();
	String PageEntity;
	String requiredId = "";
	Boolean isCrud = false;
	/**
	 * Constructeur avec une variable le nom de la page
	 * @param PageName est le nom de la page
	 */
	public Page(String pageName){
		this.PageName=pageName;
		
	}
	/**
	 * Set Template
	 * @param temp est le Template
	 * @param tName est le nom du Template
	 */
	public void setTemplate(Template temp, String tName){
		this.PageTemplate=temp;
		this.TemplateName=tName;	
	}
	
	/**
	 * Set CRUD à vrais
	 */
	public void setCrud(){	
	this.isCrud=true;
	}
	
	/**
	 * Récupérer le nom de la page
	 * @return
	 */
	public String getPageName() {
		return PageName;
	}
	
	/**
	 * Récupérer le nom de la Template
	 * @return
	 */
	public String getTemplateName() {
		return TemplateName;
	}
	
	/**
	 * Set page name
	 * @param pageName est le nom de la page
	 */
	public void setPageName(String pageName) {
		PageName = pageName;
	}
	
	/**
	 * Récupérer l'entité
	 * @return
	 */
	public String getEntity() {
		return PageEntity;
	}

	/**
	 * Set Entité
	 * @param Entity est le nom de l'entité
	 */
	public void setEntity(String Entity) {
		PageEntity = Entity;
	}

	/**
	 * Ajouter un attribut dans la liste de attributs
	 * @param Attribut est le nom de l'attribut
	 */
	
	public void setAttribute(String Attribut) {
		
		ListAttributs.add(Attribut);
	}
	
	/**
	 * Récupérer la liste des attributs
	 * @param ListAttributs
	 */
	public List<String> getListAttributes() {
		return ListAttributs;
	}
	
	
	/**
	 * add simple function 
	 * @param Function
	 */
	public void SendFunctions(VelocityContext c){
			c.put("listFunction", ListFunctions);
	}
	
	/**
	 * Ajouter une fonction dans la liste des fonction
	 * @param Function est le nom de la fontion
	 */
	public void setFunction(String Function)
	{
		ListFunctions.add(Function);		
	}
	
	/**
	 * Récupérer la liste des fonctions
	 * @return
	 */
	public List<String> GetListFunction()
	{		
		return ListFunctions;
	}

	/**
	 * Générer les Views
	 * @param context est une instance de l'objet VelocityContext
	 */
	public void generate(VelocityContext context)
	{

		StringWriter writer = new StringWriter();
		this.PageTemplate.merge(context, writer);
		try {
			FileWriter fw;
			fw = new FileWriter(System.getProperty("user.dir")+"/AngularNew/Src/View/"+this.PageName+".html");
			fw.write(writer.toString());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * Récupérer le RequiredId
	 * @return
	 */
	public String getRequiredId() {
		return requiredId;
	}

	/**
	 * set getRequiredId
	 * @param requiredId
	 */
	public void setRequiredId(String requiredId) {
		this.requiredId = requiredId;
	}
	// End Classe
}
