package ObjectsPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import objectsQcm.Entity;
import ObjectsEcran.Ecran;

/**
 * @author djerbienne
 *
 */
public class Page {
	String PageName;
	Map<String, Object> ListFunctions = new HashMap<String, Object>();
	Map<String, String>  ListAttributs = new HashMap<String, String>();
	public String getPageName() {
		return PageName;
	}

	public void setPageName(String pageName) {
		PageName = pageName;
	}

	/**
	 * @param e
	 * @param ListAttributs
	 */
	
	public void SetAttributs(String Attribut) {
		
		ListAttributs.put(Attribut,Attribut);
	}
	
	/**
	 * @param e
	 * @param ListAttributs
	 */
	public Map<String, String> GetListAttributs() {
		return ListAttributs;
	}
	
	
	/**
	 * add simple function 
	 * @param Function
	 */
	
	public void SetFunction(String Function)
	{
		ListFunctions.put(Function, "true");		
	}
	
	/**
	 * @param Function
	 * @return
	 */
	public Object GetFunction(String Function)
	{
		return ListFunctions.get(Function);
	}
	public Map<String, Object> GetListFunction()
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
	// End Classe
}
