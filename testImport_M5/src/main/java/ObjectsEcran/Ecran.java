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
	public Ecran()
	{

	}
	public Ecran(String EcranName)
	{
		this.EcranName=EcranName;
		
	}
	public void setEntity(String ent)
	{
		this.Entityctrl=ent;
	}
	public String getEntity(){
		return this.Entityctrl;
	}
	public String getEcranName() {
		return EcranName;
	}
	public void setEcranName(String ecranName) {
		EcranName = ecranName;
	}
	public void setList(ArrayList<String> ListOptionsTemplates)
	{
		
		for(int i = 0; i<ListOptionsTemplates.size();i++)
		{
			this.setEcran(ListOptionsTemplates.get(i), "default");
		}
		
		
	}
	public void setEcran(String Template, String Value)
	{
		EcransValue.put(Template, Value);
		EcransTitle.put(EcransTitle.size(),Template);
	}
	public String getEcran(String Ecran)
	{
		return EcransValue.get(Ecran);
	}
	
	public void setPage(Template t)
	{
		pageTemplate = t;		
	}
	public Template getPage()
	{		
		return pageTemplate;
	}
	public void updateFunctions(){
		
		for(Page p:listPages)
		{
			List<String> tempList = p.GetListFunction();
			for(String str:tempList)
			{
				this.addFunction(str);
			}
			
		}
		
		
	}
	public void updateEntities(){
		
		for(Page p:listPages)
		{
			String tempString = p.getEntity();
			if(!this.listEntities.contains(tempString))
				this.listEntities.add(tempString);
			
		}
		
		
	}
	public void updateAttributes(){
		
		for(Page p:listPages)
		{
			List<String> tempList = p.getListAttributes();
			for(String str:tempList)
			{
				this.addAttributes(str);
			}
			
		}
		
		
	}
	public void addFunction(String function){
		if(!this.listFunctions.contains(function))
		{
			this.listFunctions.add(function);
		}
		
	}
	public void addAttributes(String attr){
		if(!this.listAttributes.contains(attr))
		{
			this.listAttributes.add(attr);
		}
		
	}
	public List<String> getListAttributes(){
		return this.listAttributes;
	}
	public List<String> getListEntities(){
		return this.listEntities;
	}
	public String getListEcran(){
		String test = "";
		for(int i = 0; i<EcransTitle.size();i++)
		{
			test +="-" +EcransTitle.get(i);
		}
		return test;
		
	
	}
	
	public void printFunctions(){
		System.out.println(this.listFunctions);
	}
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
	public void addPage(Page p){
		if(!this.listPages.contains(p))
		{
			this.listPages.add(p);
		}
	}
	
	
	public void setContext(VelocityContext c)
	{
		for(int i = 0; i<EcransTitle.size();i++)
		{
			c.put(EcransTitle.get(i),EcransValue.get(EcransTitle.get(i)));
			System.out.println(EcransTitle.get(i));
		}				
	}
	
}
