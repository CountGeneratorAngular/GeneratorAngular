package ObjectsEcran;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public class Ecran {

	Map<String, String> EcransValue = new HashMap<String, String>();
	Map<Integer, String> EcransTitle = new HashMap<Integer, String>();	
	Template pageTemplate ;
	String EcranName;
	public Ecran()
	{

	}
	public Ecran(String EcranName)
	{
		this.EcranName=EcranName;
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
	
	public String getListEcran(){
		String test = "";
		for(int i = 0; i<EcransTitle.size();i++)
		{
			test +="-" +EcransTitle.get(i);
		}
		return test;
		
	
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
