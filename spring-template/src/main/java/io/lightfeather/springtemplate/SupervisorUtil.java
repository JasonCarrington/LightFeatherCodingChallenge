package io.lightfeather.springtemplate;

import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*; 
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.nio.charset.StandardCharsets;
import io.lightfeather.springtemplate.Supervisor;

public class SupervisorUtil {
	
	//Gets the supervisor data from the website as an inputStream
	public static InputStream readUrl(String url) throws MalformedURLException, IOException{
		  URLConnection connection = null;
		  connection = new URL(url).openConnection();
		  InputStream response = null;
		  return connection.getInputStream();
	  }

	//Parses InputStream into JSONArray 
	  public static JSONArray parseJsonArray(InputStream response) throws IOException, ParseException{  
		  String responseString = new String(response.readAllBytes(), StandardCharsets.UTF_8);	  
		  JSONParser parser = new JSONParser();
		  Object obj = parser.parse(responseString);	 
		 return (JSONArray) obj;
	  }
	  
	  //Removes any supervisor with a numeric jurisdiction and converts the rest into a List of Supervisors 
	  public static ArrayList<Supervisor> removeNumber(JSONArray jsonArray) throws Exception{
		  ArrayList<Supervisor> supervisorList = new ArrayList<Supervisor>();
		  for(Iterator iterator = jsonArray.iterator(); iterator.hasNext();) {
			  JSONObject json = (JSONObject) iterator.next();
			  String jurisdiction = json.get("jurisdiction").toString();
			  if ( !Character.isDigit(jurisdiction.charAt(0)) ) {
			  	supervisorList.add(new Supervisor(json));
			  }
		  }
		  return supervisorList;
	  }
	  
	  //Formats a list of Supervisors into a list of strings for the Get call.
	  public static ArrayList<String> formatList(ArrayList<Supervisor> unformattedList){
		  ArrayList<String> formattedLsit = new ArrayList<String>();
		  for(Iterator iterator = unformattedList.iterator(); iterator.hasNext();) {
			  Supervisor supervisor = (Supervisor) iterator.next();
			  formattedLsit.add(supervisor.toString());
		  }	  
		  return formattedLsit;
	  }
	  
	  //Sorts the list of supervisors by Jurisdiction, then Last Name, then First Name
	  public static void sortList(ArrayList<Supervisor> list) {
			Collections.sort(list, Comparator.comparing(Supervisor::getJurisdiction)
		            .thenComparing(Supervisor::getLastName)
		            .thenComparing(Supervisor::getFirstName));
		}


}
