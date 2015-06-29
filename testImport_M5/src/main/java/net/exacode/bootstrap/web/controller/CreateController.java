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
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import objectsQcm.Attribute;
import objectsQcm.Entity;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import antlr.collections.List;

/**
 * Presents how to pass some values to controller using URL.
 * 
 * @author pmendelski
 * 
 */
@Controller
public class CreateController {

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String greetRequest(ModelMap model) {
		ArrayList<String> factoryList = new ArrayList<String>();
		ArrayList<String[]> listAttribute= new ArrayList<String[]>();
		ArrayList<Entity> EntitiesList = new ArrayList<Entity>();
		ArrayList<String[]> ParentList = new ArrayList<String[]>();
		ArrayList<String[]> liste = new ArrayList<String[]>();
		ArrayList<String[]> Entities = new ArrayList<String[]>();
		ArrayList<String[]> Struct = new ArrayList<String[]>();
		ArrayList<String[]> StructSorted = new ArrayList<String[]>();
		ArrayList<String> External = new ArrayList<String>();
		Entity globalDBB = new Entity("DBB",false);
		
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
		
		
		String Path = CreateController.class.getClassLoader().getResource("").toString();
		
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
		ParentList.add(new String[]{voiture.getName()});
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

		getEntitiesAndType(globalDBB, Struct, External,0);
		
		
		for(int i =0; i<Entities.size();i++)
		{	
			for(int j=0;j< Entities.get(i).length;j++)
			{
				System.out.print(Entities.get(i)[j]+" ");	
			}
			System.out.println();
		}
		
		
		
		
		for(int i =0; i<Struct.size();i++)
		{	
			for(int j=0;j< Struct.get(i).length;j++)
			{
			System.out.print(Struct.get(i)[j]+" ");
			}
			System.out.println();
		}

		
		
		
		
		
		Object[] listFields = new Object[liste.size()];
		Object[] fieldsName = new Object[liste.size()];

		
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
		//********** ecrire un fichier ***********//
		//System.out.println(new File("").getAbsolutePath());
		 //STEP 1 :Create a VelocityTemplate Engine Object
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		System.out.println(ClasspathResourceLoader.class.getName());
		velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		 //STEP 2 :Initialise VelocityTemplate Engine //
		velocityEngine.init();
		 //STEP 3: Create the template object by calling getTemplate() method in VE(VelocityEngine)//
		
		
		new File(System.getProperty("user.dir")+"/AngularNew/").mkdir();
		new File(System.getProperty("user.dir")+"/AngularNew/Src/").mkdir();
		new File(System.getProperty("user.dir")+"/AngularNew/Server/").mkdir();
		new File(System.getProperty("user.dir")+"/AngularNew/Src/Route/").mkdir();
		new File(System.getProperty("user.dir")+"/AngularNew/Src/Factory/").mkdir();
		new File(System.getProperty("user.dir")+"/AngularNew/Src/Service/").mkdir();
		new File(System.getProperty("user.dir")+"/AngularNew/Src/View/").mkdir();
		new File(System.getProperty("user.dir")+"/AngularNew/Src/Controller/").mkdir();
		new File(System.getProperty("user.dir")+"/AngularNew/Js").mkdir();

		
		DuplicateFile(System.getProperty("user.dir")+"/angular.js",System.getProperty("user.dir")+"/AngularNew/Js/angular.js");
		DuplicateFile(System.getProperty("user.dir")+"/angular-route.js",System.getProperty("user.dir")+"/AngularNew/Js/angular-route.js");
		DuplicateFile(System.getProperty("user.dir")+"/angular-resource.js",System.getProperty("user.dir")+"/AngularNew/Js/angular-resource.js");
		DuplicateFile(System.getProperty("user.dir")+"/checklist-model.js",System.getProperty("user.dir")+"/AngularNew/Js/checklist-model.js");

		 //STEP 4: Create VelocityContext //
		VelocityContext context = new VelocityContext();
		context.put("controller", "controller");
		//context.put("EntitiesConstruct", EntitiesObject);
		context.put("AppName", "Test");
		System.out.println(Struct.get(0)[4]);
		StructSorted=Struct;
		
		Collections.sort(StructSorted, new DepthComparator());

		for(int i =0;i<StructSorted.size();i++)
		{
			StructObjectSorted[i]=StructSorted.get(i);
			
		}
		
		
		for(int i=0;i<StructObjectSorted.length;i++)
		{
			System.out.println(StructObjectSorted[i]);
			
		}
		
		
		
		
		
		context.put("entitiesReversal", StructObjectSorted);
		context.put("structure", StructObject);
		context.put("external", ExtObject);
		
		Template templateapp = velocityEngine.getTemplate("/templates/app.vm");
		createFile(templateapp, context, "Src/app", "js");
		Template templateServer = velocityEngine.getTemplate("/templates/server.vm");
		createFile(templateServer, context, "Server/server", "js");

		Template templateRouting = velocityEngine.getTemplate("/templates/routing.vm");
		createFile(templateRouting, context, "Src/Route/routing", "js");

		Template templateSharedData = velocityEngine.getTemplate("/templates/sharedData.vm");
		createFile(templateSharedData, context, "Src/Service/sharedData", "js");

		Template templateHome = velocityEngine.getTemplate("/templates/home.vm");
		createFile(templateHome, context, "Src/View/home", "html");
		Template templateFactory = velocityEngine.getTemplate("/templates/Factory.vm");
		Template templateListView = velocityEngine.getTemplate("/templates/listView.vm");
		Template templateEditView = velocityEngine.getTemplate("/templates/editView.vm");
		Template templateCreateView = velocityEngine.getTemplate("/templates/createView.vm");

		Object[] EntityFirstLevelObject;
		for(int k = 0; k<EntitiesList.size(); k++)
		{
			System.out.println(Struct.get(k)[4]);
			getEntitiesAttributeFirstLevel(EntitiesList.get(k),listAttribute);
			context.put("EntityName", listAttribute.get(0)[0]);
			EntityFirstLevelObject = new Object[listAttribute.size()];
			for(int i =1;i<listAttribute.size();i++)
			{
				EntityFirstLevelObject[i]=listAttribute.get(i);
			} 
			context.put("Attributes", EntityFirstLevelObject);
			context.put("Parent", ParentList.get(k));
			createFile(templateFactory, context, "Src/Factory/"+listAttribute.get(0)[0]+"Factory", "js");	
			createFile(templateListView, context, "Src/View/"+listAttribute.get(0)[0]+"ListView", "html");	
			createFile(templateEditView, context, "Src/View/"+listAttribute.get(0)[0]+"EditView", "html");	
			createFile(templateCreateView, context, "Src/View/"+listAttribute.get(0)[0]+"CreateView", "html");
			factoryList.add(listAttribute.get(0)[0]);
			Template TemplateController = velocityEngine.getTemplate("/templates/Controller.vm");
			createFile(TemplateController, context, "Src/Controller/"+listAttribute.get(0)[0]+"Controller", "js");
			listAttribute.clear();
		}

		Object[] EntityNameList = new Object[factoryList.size()];
		for(int i =0;i<factoryList.size();i++)
		{
			EntityNameList[i]=factoryList.get(i);
			System.out.println(factoryList.get(i));
		}
		context.put("factoryList",EntityNameList);
		Template templateIndex = velocityEngine.getTemplate("/templates/index.vm");
		createFile(templateIndex, context, "index", "html");
		
		
		
		

		  //System.out.println(writer);

		return "createObjet";
	}
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
	
}
