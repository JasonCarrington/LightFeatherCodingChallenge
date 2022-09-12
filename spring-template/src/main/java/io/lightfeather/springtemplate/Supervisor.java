/**
 * 
 */
package io.lightfeather.springtemplate;

import java.lang.*;
import org.json.simple.*;
/**
 * @author Jason
 * 
 * This will hold Supervisor information 
 */
public class Supervisor {
	
	String jurisdiction;
	String lastName;
	String firstName;
	String email;
	String phone;
	String supervisor;
	
	public Supervisor(JSONObject json){
		jurisdiction = getIfNotNull(json, "jurisdiction");
		lastName = getIfNotNull(json, "lastName");
		firstName = getIfNotNull(json, "firstName");
		email = getIfNotNull(json, "email");
		phone = getIfNotNull(json, "phoneNumber");
		supervisor = getIfNotNull(json ,"supervisor");
	}
	
	//Returns a string value from the json if it exists and returns empty string if it does not.
	public static String getIfNotNull(JSONObject json, String key) {
		Object value = json.get(key);
		if(value!=null) {
			return value.toString();
		}
		return "";
	}
	
	//Validates the required fields
	public void validate() throws Exception{
		if(supervisor.length()<1 || 
				lastName.length()<1 ||
				firstName.length()<1) {
			throw new Exception("supervisor, lastName, and firstName are required fields");
		}
	}
	
	public String getJurisdiction() {
		return jurisdiction;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getSupervisor() {
		return supervisor;
	}

	//Converts Supervisor into a formated string to fullfil the GET request
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(getJurisdiction());
		sb.append(" - ").append(getLastName()).append(", ").append(getFirstName());
		return sb.toString();
	}
	
	//Converts Supervisor into a string containing the data of a POST request
	public String submittedData() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Last Name : ").append(getLastName()).append("\n");
		sb.append("First Name : ").append(getFirstName()).append("\n");
		sb.append("Supervisor : ").append(getSupervisor()).append("\n");
		if(getEmail().length()>0) {
			sb.append("Email : ").append(getEmail()).append("\n");
		}
		if(getPhone().length()>0) {
			sb.append("Phone : ").append(getPhone()).append("\n");
		}
		return sb.toString();
	}
	
}
