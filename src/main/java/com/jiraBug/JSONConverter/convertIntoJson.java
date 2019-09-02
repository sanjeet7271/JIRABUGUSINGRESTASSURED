package com.jiraBug.JSONConverter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import com.jiraBug.configreader.GlobalReader;

public class convertIntoJson extends GlobalReader {

	String KEY = prop.getProperty("PROJECTID");
	String SUMARY = prop.getProperty("SUMARY");
	String DESCRIPTION = prop.getProperty("DESCRIPTION");
	String BUGPRIORITY_VALUE = prop.getProperty("BUGPRIORITY_VALUE");
	String ISSUETYPE = prop.getProperty("ISSUETYPE");
	String ACTIVESPRINT_CUSTOMID = prop.getProperty("ACTIVESPRINT_CUSTOMID");
	String STEPSTOREPRODUCE_CUSTOMID = prop.getProperty("STEPSTOREPRODUCE_CUSTOMID");
	String ACTUALRESULT_CUSTOMID = prop.getProperty("ACTUALRESULT_CUSTOMID");
	String EXPECTEDRESULT_CUSTOMID = prop.getProperty("EXPECTEDRESULT_CUSTOMID");
	String BUGPRIORITY_CUSTOMID = prop.getProperty("BUGPRIORITY_CUSTOMID");

	public convertIntoJson() throws IOException {
		super();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String JsonConverter(String Key,String Summary,String Description,String ActiveSprint, String StepsToProduce,String ActualResult,String ExpectedResult,String IssueType,String BugPriority) throws FileNotFoundException {
		
		JSONObject obj = new JSONObject();
		JSONObject Jsonobj = new JSONObject();

		Map map = new LinkedHashMap();
		map.put(KEY, Key);
		obj.put("project", map);

		obj.put(SUMARY, Summary);
		obj.put(DESCRIPTION, Description);
		obj.put(ACTIVESPRINT_CUSTOMID, Integer.parseInt(ActiveSprint));
		obj.put(STEPSTOREPRODUCE_CUSTOMID, StepsToProduce);
		obj.put(ACTUALRESULT_CUSTOMID, ActualResult);
		obj.put(EXPECTEDRESULT_CUSTOMID, ExpectedResult);
		
		Map map1 = new LinkedHashMap();
		map1.put(ISSUETYPE, IssueType);
		obj.put("issuetype", map1);

		Map map2 = new LinkedHashMap();
		map2.put(BUGPRIORITY_VALUE, BugPriority);
		obj.put(BUGPRIORITY_CUSTOMID, map2);
		
		
		Jsonobj.put("fields", obj);
		
		System.out.println(obj.toJSONString());
		PrintWriter pw = new PrintWriter("./src/main/resources/json/BugJson.json");
		pw.write(obj.toJSONString());
		pw.flush();
		pw.close();
		
		return Jsonobj.toJSONString();

	}

}
