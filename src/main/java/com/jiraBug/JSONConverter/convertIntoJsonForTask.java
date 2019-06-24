package com.jiraBug.JSONConverter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.jiraBug.configreader.GlobalReaderForTask;

public class convertIntoJsonForTask extends GlobalReaderForTask {
	String ProjectID = property.getProperty("ProjectID");
	String summary = property.getProperty("summary");
	String description = property.getProperty("description");
	String Priority = property.getProperty("Priority");
	String IssueType = property.getProperty("IssueType");
	
	public convertIntoJsonForTask() throws IOException {
		super();
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object JsonConverter() throws FileNotFoundException {
		
		JSONObject obj = new JSONObject();
		JSONObject Jsonobj = new JSONObject();

		Map map = new LinkedHashMap();
		map.put("key", ProjectID);
		obj.put("project", map);

		obj.put("summary", summary);
		obj.put("description", description);
		
		Map map1 = new LinkedHashMap();
		map1.put("name", IssueType);
		obj.put("issuetype", map1);

		Map map2 = new LinkedHashMap();
		map2.put("name", Priority);
		obj.put("priority", map2);
		
		Jsonobj.put("fields", obj);

		PrintWriter pw = new PrintWriter("./src/main/resources/json/TaskJson.json");
		pw.write(obj.toJSONString());
		pw.flush();
		pw.close();
		
		return Jsonobj;

	}

}
