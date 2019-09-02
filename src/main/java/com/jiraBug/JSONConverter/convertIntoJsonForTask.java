package com.jiraBug.JSONConverter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import com.jiraBug.configreader.GlobalReader;

public class convertIntoJsonForTask extends GlobalReader {
	String KEY = prop.getProperty("PROJECTID");
	String SUMARY = prop.getProperty("SUMARY");
	String DESCRIPTION = prop.getProperty("DESCRIPTION");
	String PRIORITY_VALUE = prop.getProperty("PRIORITY_VALUE");
	String ISSUETYPE = prop.getProperty("ISSUETYPE");
	String ACTIVESPRINT_CUSTOMID = prop.getProperty("ACTIVESPRINT_CUSTOMID");
	
	public convertIntoJsonForTask() throws IOException {
		super();
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject JsonConverter(String Key,String Summary,String Description,String ActiveSprint, String IssueType,String BugPriority) throws FileNotFoundException {
		
		JSONObject obj = new JSONObject();
		JSONObject Jsonobj = new JSONObject();

		Map map = new LinkedHashMap();
		map.put(KEY, Key);
		obj.put("project", map);

		obj.put(SUMARY, Summary);
		obj.put(DESCRIPTION, Description);
		obj.put(ACTIVESPRINT_CUSTOMID, Integer.parseInt(ActiveSprint));
		
		Map map1 = new LinkedHashMap();
		map1.put(ISSUETYPE, IssueType);
		obj.put("issuetype", map1);

		Map map2 = new LinkedHashMap();
		map2.put(PRIORITY_VALUE, BugPriority);
		obj.put("priority", map2);
		
		Jsonobj.put("fields", obj);

		PrintWriter pw = new PrintWriter("./src/main/resources/json/TaskJson.json");
		pw.write(obj.toJSONString());
		pw.flush();
		pw.close();
		
		return Jsonobj;

	}

}
