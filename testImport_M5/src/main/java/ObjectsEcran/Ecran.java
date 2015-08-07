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
	public Ecran()
	{

	}
	
	public void setList(ArrayList<String> ecrans)
	{
		
		for(int i = 0; i<ecrans.size();i++)
		{
			this.setEcran(ecrans.get(i), "default");
		}
		
		
	}
	public void setEcran(String Ecran, String Value)
	{
		EcransValue.put(Ecran, Value);
		EcransTitle.put(EcransTitle.size(),Ecran);
		
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
