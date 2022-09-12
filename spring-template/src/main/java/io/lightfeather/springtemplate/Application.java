package io.lightfeather.springtemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*; 
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.nio.charset.StandardCharsets;
import io.lightfeather.springtemplate.Supervisor;
import io.lightfeather.springtemplate.SupervisorUtil;


@SpringBootApplication
@RestController
public class Application {
  
 
	@PostMapping("/api/submit")
	public ResponseEntity<String> submitSupervisor(@RequestBody JSONObject json) {
		
		Supervisor supervisor = new Supervisor(json);
		try {
			supervisor.validate();
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		System.out.println(supervisor.submittedData());
		return ResponseEntity.status(HttpStatus.OK).body("Supervisor posted to console");
	}
	

  @RequestMapping("/api/supervisors")
  public Object getSupervisors() {
	  InputStream response = null;
	  try {
		  String url = "https://o3m5qixdng.execute-api.us-east-1.amazonaws.com/api/managers"; 
		  response = SupervisorUtil.readUrl(url);
	  }catch(MalformedURLException e) {
		  return "A problem has occured with the supervisor URL";
	  }catch(IOException e) {
		  return "A problem has occured with the supervisor data";
	  }
	  JSONArray jsonArray = null;
	  ArrayList<Supervisor> supervisorList = null;
	  try {
	     jsonArray = SupervisorUtil.parseJsonArray(response);	
	     supervisorList = SupervisorUtil.removeNumber(jsonArray);
	  }catch(Exception e) {
		  return "The supervisor data could not be parsed";
	  }
	  
	  SupervisorUtil.sortList(supervisorList);
	  
	  ArrayList<String> formattedSupervisors = SupervisorUtil.formatList(supervisorList);
	  
	  return formattedSupervisors;
  }
  
  
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }


}
