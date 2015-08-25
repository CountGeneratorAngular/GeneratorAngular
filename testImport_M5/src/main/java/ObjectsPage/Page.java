package ObjectsPage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

import objectsQcm.Entity;
import ObjectsEcran.Ecran;

/**
 * @author djerbienne
 *
 */
public class Page {
	String PageName;
	Template PageTemplate;
	List<String> ListFunctions = new ArrayList<String>();
	List<String>  ListAttributs = new ArrayList<String>();
	String PageEntity;
	
	
	public Page(String pageName){
		this.PageName=pageName;
		
	}
	public void setTemplate(Template temp){
		this.PageTemplate=temp;
		
	}
	public String getPageName() {
		return PageName;
	}

	public void setPageName(String pageName) {
		PageName = pageName;
	}
	public String getEntity() {
		return PageEntity;
	}

	public void setEntity(String Entity) {
		PageEntity = Entity;
	}

	/**
	 * @param e
	 * @param ListAttributs
	 */
	
	public void SetAttribute(String Attribut) {
		
		ListAttributs.add(Attribut);
	}
	
	/**
	 * @param e
	 * @param ListAttributs
	 */
	public List<String> GetListAttributs() {
		return ListAttributs;
	}
	
	
	/**
	 * add simple function 
	 * @param Function
	 */
	public void SendFunctions(VelocityContext c){
			c.put("listFunction", ListFunctions);
	}
	public void SetFunction(String Function)
	{
		ListFunctions.add(Function);		
	}
	
	/**
	 * @param Function
	 * @return
	 */
	public Object GetListFunction(String Function)
	{
		
		
		return ListFunctions;
	}

	/**
	 * add liste function 
	 * @param Function
	 */
	public void SetListFunction(List<String> ListFunctions)
	{
		
	}
	public void generateCtrlFact(Context context, Template templaceController, Template templaceFactory){
		Template tempTemplate = this.PageTemplate;
		String tempName = this.PageName;
		this.setTemplate(templaceController);
		this.setPageName("Controller");
		this.generate(context);
		this.setTemplate(templaceFactory);
		this.setPageName("Factory");
		this.generate(context);
		this.setTemplate(tempTemplate);
		this.setPageName(tempName);

		
	}
	
	public void selectFrom(List<String> selection,String entity,List<Entity> entitiesList   )
	{
		
		
	}
	public void generate(Context context){
		
		StringWriter writer = new StringWriter();
		PageTemplate.merge(context, writer);
		String ext = "";
		if(this.PageName =="Factory" || this.PageName == "Controller")
			{
			ext = "js";
			this.PageName="Src/"+this.PageName+"/QcmTest"+this.PageName;
			}
		else
			{
			ext = "html";
			this.PageName="Src/View/"+this.PageName;
			}
		
		try {
			FileWriter fw;
			fw = new FileWriter(System.getProperty("user.dir")+"/AngularNew/"+this.PageName+"."+ext);
			fw.write(writer.toString());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
}
	// End Classe
}
