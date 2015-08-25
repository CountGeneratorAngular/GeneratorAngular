package net.exacode.bootstrap.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import objectsQcm.Attribute;
import objectsQcm.Entity;
import objectsQcm.entityVelocity;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ObjectsEcran.Ecran;
import ObjectsPage.Page;

/**
 * Presents how to pass some values to controller using URL.
 * 
 * @author Imen & Vincent
 * 
 */

@Controller
public class CreateController {

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String greetRequest(ModelMap model) {
		
// definition des variables
		ArrayList<String> factoryList = new ArrayList<String>();//factory=usine
		ArrayList<String[]> listAttribute= new ArrayList<String[]>();
		ArrayList<Entity> EntitiesList = new ArrayList<Entity>();
		ArrayList<String[]> ParentList = new ArrayList<String[]>();
		//ArrayList<String[]> liste = new ArrayList<String[]>();
		//ArrayList<String[]> Entities = new ArrayList<String[]>();
		ArrayList<String[]> Struct = new ArrayList<String[]>();
		ArrayList<String[]> StructSorted = new ArrayList<String[]>(); //Sorted=trié
		ArrayList<String> External = new ArrayList<String>();
		Entity globalDBB = new Entity("DBB",false);
		
// definition des entites 
		EntityDefinition(globalDBB, EntitiesList, ParentList);
		
// creation des listes pour Velocity a partir des entites
		getEntitiesAndType(globalDBB, Struct, External,0);
		Object[] StructObject = new Object[Struct.size()];
		Object[] StructObjectSorted = new Object[Struct.size()];
		Object[] ExtObject = new Object[External.size()];
		Object[] ParentListObject = new Object[ParentList.size()];
		for(int i =0;i<Struct.size();i++)
		{
			StructObject[i]=Struct.get(i);
		}
		for(int i =0;i<External.size();i++)
		{
			ExtObject[i]=External.get(i);
		}
		for(int i =0;i<ParentList.size();i++)
		{
			ParentListObject[i]=ParentList.get(i);
		}
		//
		
// initialisation de Velocity
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		velocityEngine.init();
// Creation des dossiers du programme AngularJs
		createStruct();
		VelocityContext context = new VelocityContext();		
		context.put("AppName", "Test");
		StructSorted=Struct;
		Collections.sort(StructSorted, new DepthComparator());
		for(int i =0;i<StructSorted.size();i++)
		{
			StructObjectSorted[i]=StructSorted.get(i);	
		}
		context.put("entitiesReversal", StructObjectSorted); // Reversal=renversement
		context.put("structure", StructObject);
		context.put("external", ExtObject);
		
// mise en place des templates
		Map<String, Template> mapTemplates = new HashMap<String, Template>();		
		setTemplates(mapTemplates, velocityEngine);				
		
/* creation et mise en place des ecrans
		Ecran EListView = new Ecran();
		SetEcranListView(EListView);
		EListView.setEcran("templateView", "entityListCrud.vm");
		EListView.setEcran("templateMenu", "entityCreateOptionsCrud.vm");
		EListView.setPage(mapTemplates.get("listView"));	
		*/		
//Meta-Modeling
		/*Create Ecran*/
		Ecran e =new Ecran("QCM");
		/*Create Page*/
		Page QcmPage= new Page();
		Page VoiturePage= new Page();
		/*Create Entity*/
		  Entity qcmTest = new Entity("qcmTest", false);
		  qcmTest.AddAttribute(new Attribute("id", "Number", false));
		  qcmTest.AddAttribute(new Attribute("Titre", "String", false));
		  qcmTest.AddAttribute(new Attribute("Repondu", "Boolean", false));
		  	  
		  /* 
		   * declaration des attributs à afficher
		   * Ajouter une list
		   * Replace Attributes
		   * self.selectionAllGlobal=['id', 'moteur', 'ClasseId', 'SmileyId', 'MatiereAgr', 'Roues', 'vitre'];
		   * */
		  /*
		   * A remplacer par QcmPage.SetAttributs("qcmTest","id","Titre"...);
		   */
		QcmPage.SetAttributs("qcmTest","id");
		QcmPage.SetAttributs("qcmTest","Titre");
		VoiturePage.SetAttributs("Voiture", "id");
		/*
		 * ajouter une seule fonction
		 */		
		
		QcmPage.SetFunction("edit");
		QcmPage.SetFunction("Save");
		QcmPage.SetFunction("create");
		QcmPage.SetFunction("Create");
		QcmPage.SetFunction("Redirect");			
		QcmPage.SetFunction("goInto");	
		QcmPage.SetFunction("delete");		
		QcmPage.SetFunction("switch");			
		QcmPage.SetFunction("new");
		QcmPage.SetFunction("restoreArrayData");
		QcmPage.SetFunction("restoreArrayDataEdit");
		QcmPage.SetFunction("showFrom");
		QcmPage.SetFunction("showFromEdit");
		
		
		
		/*
		 * Recuperer la list de s function à ajouter
		 * faire contrxt.put des functions choisis par l'user
		 * tester
		 */
		
		Map<String, String> ListFunctions = new HashMap<String, String>();
		ListFunctions= QcmPage.GetListFunction();
		
		/*for(int i=0; i<ListFunctions.size();i++)
		{
			*/
			for (final Map.Entry<String, String> entry : ListFunctions.entrySet())
			{
				System.out.println("*************************Bigin: ListFunctions*****************************");
				System.out.println(entry.getKey());
				System.out.println(entry.getValue());
				System.out.println("*************************End: ListFunctions*****************************");
				context.put(entry.getKey(), String.valueOf(entry.getValue()));
			}
		//}
			
// creation des fichiers angularJs independants des entites
		createFile(mapTemplates.get("app"), context, "Src/app", "js");
		createFile(mapTemplates.get("server"), context, "Server/server", "js");
		createFile(mapTemplates.get("routing"), context, "Src/Route/routing", "js");
		createFile(mapTemplates.get("sharedData"), context, "Src/Service/sharedData", "js");
		createFile(mapTemplates.get("home"), context, "Src/View/home", "html");
		createFile(mapTemplates.get("naviGatController"), context, "Src/Controller/NaviGatController", "js");
		//createFile(mapTemplates.get("logsController"), context, "Src/Controller/logsController", "js");
		//createFile(mapTemplates.get("indexController"), context, "Src/Controller/indexController", "js");
		//createFile(mapTemplates.get("logsController"), context, "Src/View/logsController", "html");
		//createFile(mapTemplates.get("logsFactory"), context, "Src/Factory/logsFactory", "js");

// creation des fichiers AngularJs dependant des entites
		Object[] EntityFirstLevelObject;
		/*
		 * Get list Attributs choisis par l'utilisateur
		 */
		Map<String, String>  ListAttributsObject = QcmPage.GetListAttributs();
	
		//EListView.setContext(context);
		ArrayList<entityVelocity> entitiesVelocity = new ArrayList<entityVelocity>();
		ArrayList<String> ListEntities= QcmPage.GetListEntity();

		for(int k = 0; k<EntitiesList.size(); k++)
		{
			getEntitiesAttributeFirstLevel(EntitiesList.get(k),listAttribute); 
			context.put("EntityName", listAttribute.get(0)[0]);
			 String[] str = (String[]) ListAttributsObject.keySet().toArray(new String[ListAttributsObject.size()]);
			 EntityFirstLevelObject = new Object[listAttribute.size()];
				
				if(ListEntities.size()!=0)
				{
					for(int P=0; P<ListEntities.size();P++)
					{
						System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
						System.out.println(ListEntities.get(P));
						System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
						System.out.println(listAttribute.get(0)[0]);
						if(ListEntities.get(P).equals(listAttribute.get(0)[0]))
						{
							
							for(int i1=0; i1<str.length;i1++) 
							{
								for(int i =1;i<listAttribute.size();i++)
								{
						            if((str[i1].equals(listAttribute.get(i)[0])))
						            {
						            	EntityFirstLevelObject[i]=listAttribute.get(i);		
						            	break;
						            }							        
								}
							} 
						}
						else
						{
							 for(int i =1;i<listAttribute.size();i++)
								{
									EntityFirstLevelObject[i]=listAttribute.get(i);
								} 
						}
					}
					
				}
			
			
			/*
			 * enlever les doublant
			 */
	
			/*
			 * attribuer les attributs 
			 */
			//context.put("Attributes", EntityFirstLevelObject);
			
			
			/*
			 * add list attributs to the velocity
			 */
			context.put("Attributes", EntityFirstLevelObject);
			context.put("Parent", ParentList.get(k));
			entitiesVelocity.add(new entityVelocity(EntityFirstLevelObject, ParentList.get(k), listAttribute.get(0)[0]));
			createFile(mapTemplates.get("factory"), context, "Src/Factory/"+listAttribute.get(0)[0]+"Factory", "js");
			createFile(mapTemplates.get("listView"), context, "Src/View/"+listAttribute.get(0)[0]+"ListView", "html");	
			createFile(mapTemplates.get("editView"), context, "Src/View/"+listAttribute.get(0)[0]+"EditView", "html");	
			createFile(mapTemplates.get("createView"), context, "Src/View/"+listAttribute.get(0)[0]+"CreateView", "html");
			factoryList.add(listAttribute.get(0)[0]);
			createFile(mapTemplates.get("controller"), context, "Src/Controller/"+listAttribute.get(0)[0]+"Controller", "js");
			listAttribute.clear();
		}
		
		context.put("entitiesVelocity", entitiesVelocity);
		/*
		createFile(mapTemplates.get("controllerCrud"), context, "Src/Controller/controllerCrud", "js");
		createFile(mapTemplates.get("listViewCrud"), context, "Src/View/listViewCrud", "html");
		createFile(mapTemplates.get("createViewCrud"), context, "Src/View/createViewCrud", "html");
		*/
		Object[] EntityNameList = new Object[factoryList.size()];
		for(int i =0;i<factoryList.size();i++)
		{
			EntityNameList[i]=factoryList.get(i);
		}
		context.put("factoryList",EntityNameList);
		
// creation de l'index avec l'appel vers tout les fichiers AngularJs
		createFile(mapTemplates.get("index"), context, "index", "html");

		return "createObjet";
	}
	

	
	public void setTemplates(Map<String, Template> mapTemplates, VelocityEngine v){
		mapTemplates.put("app",v.getTemplate("/templates/app.vm"));
		mapTemplates.put("server",v.getTemplate("/templates/server.vm"));
		mapTemplates.put("routing",v.getTemplate("/templates/routing.vm"));
		mapTemplates.put("sharedData",v.getTemplate("/templates/sharedData.vm"));
		//mapTemplates.put("indexController",v.getTemplate("/templates/indexController.vm"));
		mapTemplates.put("home",v.getTemplate("/templates/home.vm"));
		mapTemplates.put("factory",v.getTemplate("/templates/Factory.vm"));
		//mapTemplates.put("listViewCrud",v.getTemplate("/templates/listViewCrud.vm"));
		mapTemplates.put("listView",v.getTemplate("/templates/listView.vm"));
		mapTemplates.put("editView",v.getTemplate("/templates/editView.vm"));
		mapTemplates.put("createView",v.getTemplate("/templates/createView.vm"));
		//mapTemplates.put("createViewCrud",v.getTemplate("/templates/createViewCrud.vm"));
		//mapTemplates.put("authentification",v.getTemplate("/templates/authentification.vm"));
		//mapTemplates.put("logsController",v.getTemplate("/templates/LogsController.vm"));
		//mapTemplates.put("logsFactory", v.getTemplate("/templates/logsFactory.vm"));
		mapTemplates.put("naviGatController",v.getTemplate("/templates/NaviGatController.vm"));
		mapTemplates.put("controller", v.getTemplate("/templates/Controller.vm"));
		//mapTemplates.put("controllerCrud", v.getTemplate("/templates/controllerCrud.vm"));
		mapTemplates.put("index",v.getTemplate("/templates/index.vm"));
		
	}
	public void EntityDefinition(Entity globalDBB,ArrayList<Entity> EntitiesList, ArrayList<String[]> ParentList){

		Entity qcmTest = new Entity("qcmTest", false);
		  qcmTest.AddAttribute(new Attribute("id", "Number", false));
		  qcmTest.AddAttribute(new Attribute("Titre", "String", false));
		  qcmTest.AddAttribute(new Attribute("Repondu", "Boolean", false));
		  
		  Entity questions = new Entity("questions", false);
		  questions.AddAttribute(new Attribute("id","Number", false));
		  questions.AddAttribute(new Attribute("Titre","String", false));
		  
		  Entity reponses = new Entity("reponses", false);
		  reponses.AddAttribute(new Attribute("id", "Number",false));
		  reponses.AddAttribute(new Attribute("Titre","String",false));
		  reponses.AddAttribute(new Attribute("isTrue","Boolean",false));

		  questions.AddEntity(reponses);
		  qcmTest.AddEntity(questions);
		
		
		  
		Entity classes = new Entity("Classe",false);
		classes.AddAttribute(new Attribute("id","Number",false));
		classes.AddAttribute(new Attribute("Nom","String",false));
		  
		  
		Entity voiture = new Entity("Voiture", false);
		voiture.AddAttribute(new Attribute("id", "Number", false));
		voiture.AddAttribute(new Attribute("moteur", "String", false));
		voiture.AddAttribute(new Attribute("ClasseId", "Number", false));
		voiture.AddAttribute(new Attribute("SmileyId", "Number", false));
		
		Entity Portieres = new Entity("Portieres", false);
		Portieres.AddAttribute(new Attribute("id","Number", false));
		Portieres.AddAttribute(new Attribute("ouverture","String", false));
		
		Entity roues = new Entity("Roues", false);
		roues.AddAttribute(new Attribute("id", "Number",false));
		roues.AddAttribute(new Attribute("blindage","Boolean",false));
		roues.AddAttribute(new Attribute("nombre","Number",false));
		
		Entity jantes = new Entity("jantes",false);
		jantes.AddAttribute(new Attribute("id", "Number",false));
		jantes.AddAttribute(new Attribute("chromees","Boolean",true));
		jantes.AddAttribute(new Attribute("couleur","String",false));
		
		
		Entity matiereAgr = new Entity("MatiereAgr", false);
		matiereAgr.AddAttribute(new Attribute("Parent", "String",false));
		matiereAgr.AddAttribute(new Attribute("ParentIndex", "Number",false));
		matiereAgr.AddAttribute(new Attribute("listeNote", "String",false));
		
		
		Entity matiere = new Entity("Matiere",false);
		matiere.AddAttribute(new Attribute("id", "Number",false));
		matiere.AddAttribute(new Attribute("Nom", "String",false));
		
		Entity vitre = new Entity("vitre",false);	
		vitre.AddAttribute(new Attribute("id", "Number",false));
		vitre.AddAttribute(new Attribute("limo","Boolean",false));
		
		
		Entity smiley = new Entity("smiley", false);
		smiley.AddAttribute(new Attribute("id","Number",false));
		smiley.AddAttribute(new Attribute("Nom","String",false));
		
		Entity jeu = new Entity("jeu",false);
		jeu.AddAttribute(new Attribute("id","Number",false));
		
		Entity validation = new Entity("validation",false);
		validation.AddAttribute(new Attribute("id","Number",false));
		
		Entity oculus = new Entity("oculus",false);
		oculus.AddAttribute(new Attribute("id","Number",false));
		
		Entity renferme = new Entity("renferme",false);
		renferme.AddAttribute(new Attribute("id","Number",false));
		
		jantes.AddEntity(validation);
		
		roues.AddEntity(renferme);
		roues.AddEntity(jantes);
		
		vitre.AddEntity(oculus);
		
		voiture.AddEntity(matiereAgr);
		voiture.AddEntity(roues);
		voiture.AddEntity(vitre);
		
		
		globalDBB.AddEntity(voiture);
		globalDBB.AddEntity(qcmTest);
		globalDBB.AddEntity(classes);
		globalDBB.AddEntity(smiley);
		globalDBB.AddEntity(matiere);
		
		
		EntitiesList.add(globalDBB);
		ParentList.add(new String[]{});
		EntitiesList.add(qcmTest);
		ParentList.add(new String[]{});
		EntitiesList.add(questions);
		ParentList.add(new String[]{qcmTest.getName()});
		EntitiesList.add(reponses);
		ParentList.add(new String[]{qcmTest.getName(),questions.getName()});
		EntitiesList.add(voiture);
		ParentList.add(new String[]{});
		EntitiesList.add(vitre);
		ParentList.add(new String[]{voiture.getName()});
		EntitiesList.add(oculus);
		ParentList.add(new String[]{voiture.getName(),vitre.getName()});
		EntitiesList.add(matiereAgr);
		ParentList.add(new String[]{voiture.getName()});
		EntitiesList.add(roues);
		ParentList.add(new String[]{voiture.getName()});
		EntitiesList.add(renferme);
		ParentList.add(new String[]{voiture.getName(),roues.getName()});
		EntitiesList.add(jantes);
		ParentList.add(new String[]{voiture.getName(),roues.getName()});
		EntitiesList.add(validation);
		ParentList.add(new String[]{voiture.getName(),roues.getName(),jantes.getName()});
		EntitiesList.add(smiley);
		ParentList.add(new String[]{});
		EntitiesList.add(classes);
		ParentList.add(new String[]{});
		EntitiesList.add(matiere);
		ParentList.add(new String[]{});

		
	
	}
	public void createStruct()
	{
		
		new File(System.getProperty("user.dir")+"/AngularNew/").mkdir();
		new File(System.getProperty("user.dir")+"/AngularNew/Src/").mkdir();
		new File(System.getProperty("user.dir")+"/AngularNew/Server/").mkdir();
		new File(System.getProperty("user.dir")+"/AngularNew/Src/Route/").mkdir();
		new File(System.getProperty("user.dir")+"/AngularNew/Src/Factory/").mkdir();
		new File(System.getProperty("user.dir")+"/AngularNew/Src/Service/").mkdir();
		new File(System.getProperty("user.dir")+"/AngularNew/Src/View/").mkdir();
		new File(System.getProperty("user.dir")+"/AngularNew/Src/Controller/").mkdir();
		new File(System.getProperty("user.dir")+"/AngularNew/Js").mkdir();
		new File(System.getProperty("user.dir")+"/AngularNew/img").mkdir();

		DuplicateFile(System.getProperty("user.dir")+"/angular.js",System.getProperty("user.dir")+"/AngularNew/Js/angular.js");
		DuplicateFile(System.getProperty("user.dir")+"/angular-route.js",System.getProperty("user.dir")+"/AngularNew/Js/angular-route.js");
		DuplicateFile(System.getProperty("user.dir")+"/angular-resource.js",System.getProperty("user.dir")+"/AngularNew/Js/angular-resource.js");
		DuplicateFile(System.getProperty("user.dir")+"/checklist-model.js",System.getProperty("user.dir")+"/AngularNew/Js/checklist-model.js");
		DuplicateFile(System.getProperty("user.dir")+"/splash-img.png",System.getProperty("user.dir")+"/AngularNew/img/splash-img.png");
		
		
	};
	public static void getEntitiesAttribute(Entity e, ArrayList<String[]> listAttribute,ArrayList<String[]> Entities, String parent ){
		ArrayList<String> EntityConstructor = new ArrayList<String>();
		EntityConstructor.add(e.getName());
		listAttribute.add(new String[]{parent+e.getName(),e.getClass().getSimpleName()});
		
		ArrayList<Attribute> tempAtt = e.GetAttributes();
		for(int i =0; i<tempAtt.size();i++)
		{
			listAttribute.add(new String[]{parent+tempAtt.get(i).getName(), tempAtt.get(i).getType()});
			EntityConstructor.add(tempAtt.get(i).getName());
		}
		ArrayList<Entity> tempEnt = e.GetEntities();
		for(int j = 0; j<tempEnt.size(); j++){
			
			parent+=tempEnt.get(j).getName()+">";
			getEntitiesAttribute(tempEnt.get(j), listAttribute,Entities, parent);
			EntityConstructor.add(tempEnt.get(j).getName());
		}
		String[] EntityWithAttributes = new String[EntityConstructor.size()];
		for(int k = 0; k<EntityConstructor.size();k++)
		{
			EntityWithAttributes[k]=EntityConstructor.get(k);
		}
		Entities.add(EntityWithAttributes);	
	}
	public static void getEntitiesAttributeFirstLevel(Entity e, ArrayList<String[]> listAttribute){
		listAttribute.add(new String[]{e.getName(),e.getClass().getSimpleName(),e.isArray.toString()});
		ArrayList<Attribute> tempAtt = e.GetAttributes();
		for(int i =0; i<tempAtt.size();i++)
		{
			listAttribute.add(new String[]{tempAtt.get(i).getName(), tempAtt.get(i).getType(),tempAtt.get(i).isArray.toString()});
		}
		ArrayList<Entity> tempEnt = e.GetEntities();
		for(int j = 0; j<tempEnt.size(); j++){
			listAttribute.add(new String[]{tempEnt.get(j).getName(),tempEnt.get(j).getClass().getSimpleName(),tempEnt.get(j).isArray.toString()});
		}
	}
	public static void getEntitiesAndType(Entity e, ArrayList<String[]> Struct,ArrayList<String> External, int depth){
		depth++;		
		Struct.add(new String[]{"Entity",e.getName(),"entity", e.isArray.toString(), Integer.toString(depth)});
		
		if(e.getName().substring(e.getName().length()-3).equals("Agr"))
		{
			External.add(e.getName().substring(0,e.getName().length()-3));
			
		}
		ArrayList<Attribute> tempAtt = e.GetAttributes();
		for(int i =0; i<tempAtt.size();i++)
		{
			Struct.add(new String[]{"Attribute",tempAtt.get(i).getName(), tempAtt.get(i).getType(),tempAtt.get(i).isArray.toString(), Integer.toString(depth)});
			if(tempAtt.get(i).getName().substring(tempAtt.get(i).getName().length()-2).equals("Id"))
			{
				External.add(tempAtt.get(i).getName().substring(0,tempAtt.get(i).getName().length()-2));
				
			}
		}
		ArrayList<Entity> tempEnt = e.GetEntities();
		for(int j = 0; j<tempEnt.size(); j++){
			
			getEntitiesAndType(tempEnt.get(j), Struct,External, depth);
		}
	}
	public static void createFile(Template temp, Context context, String name, String ext){
		
		StringWriter writer = new StringWriter();
		temp.merge(context, writer);
		try {
			FileWriter fw;
			fw = new FileWriter(System.getProperty("user.dir")+"/AngularNew/"+name+"."+ext);
			fw.write(writer.toString());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void DuplicateFile(String origine, String destination)
	{
		try {
			InputStream in;
			in = new FileInputStream(origine);
			OutputStream out = new FileOutputStream(destination);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
			   out.write(buf, 0, len);
			}
			in.close();
			out.close(); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void SetEcranListView(Ecran e){
		
		ArrayList<String> listOptions = new ArrayList<String>();
		listOptions.add("templateEdit");
		listOptions.add("templateView");
		listOptions.add("templateMenu");
		listOptions.add("choixData");
		e.setList(listOptions);
		
		
	}
	
}
