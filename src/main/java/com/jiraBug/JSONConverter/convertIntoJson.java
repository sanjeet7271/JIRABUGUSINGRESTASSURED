package com.jiraBug.JSONConverter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jiraBug.configreader.GlobalReader;
import com.jiraBug.excel.ExcelSheetReader;

public class convertIntoJson extends GlobalReader {
/*	
	String ProjectID = prop.getProperty("ProjectID");
	String summary = prop.getProperty("summary");
	String description = prop.getProperty("description");
	String BugPriority = prop.getProperty("BugPriority");
	String IssueType = prop.getProperty("IssueType");
	String StepsToProduce = prop.getProperty("StepsToProduce");
	String ActualResult = prop.getProperty("ActualResult");
	String ExpectedResult = prop.getProperty("ExpectedResult");
	String ActiveSprint = prop.getProperty("ActiveSprint");
	*/
	
	String KEY = prop.getProperty("KEY");
	String SUMARY = prop.getProperty("SUMARY");
	String DESCRIPTION = prop.getProperty("DESCRIPTION");
	String BUGPRIORITY_VALUE = prop.getProperty("BUGPRIORITY_VALUE");
	String ISSUETYPE = prop.getProperty("ISSUETYPE");
	String ACTIVESPRINT_CUSTOMID = prop.getProperty("ACTIVESPRINT_CUSTOMID");
	String STEPSTOREPRODUCE_CUSTOMID = prop.getProperty("STEPSTOREPRODUCE_CUSTOMID");
	String ACTUALRESULT_CUSTOMID = prop.getProperty("ACTUALRESULT_CUSTOMID");
	String EXPECTEDRESULT_CUSTOMID = prop.getProperty("EXPECTEDRESULT_CUSTOMID");
	String BUGPRIORITY_CUSTOMID = prop.getProperty("BUGPRIORITY_CUSTOMID");
/*	String key;
	String summary;
	String description;
	String ActiveSprint;
	String StepsToProduce;
	String ActualResult;
	String ExpectedResult;
	String IssueType;
	String BugPriority;*/
	String xlFilePath = "./src/main/resources/jsonData/Jira.xlsx";
	String sheetName = "BugDetails";
	ExcelSheetReader ProvideData=new ExcelSheetReader();
	Object[][] data;
	public convertIntoJson() throws IOException {
		super();
	}
	
	@DataProvider(name = "bug")
	public Object[][] CredentialtoLogin() throws Exception {
		data = ProvideData.testData(xlFilePath, sheetName);
        return data;
	}
	@Test(dataProvider="bug")@SuppressWarnings({ "rawtypes", "unchecked" })
	public String JsonConverter(String Key,String Summary,String Description,String ActiveSprint, String StepsToProduce,String ActualResult,String ExpectedResult,String IssueType,String BugPriority) throws FileNotFoundException {
		
		JSONObject obj = new JSONObject();
		JSONObject Jsonobj = new JSONObject();

		Map map = new LinkedHashMap();
		map.put(KEY, Key);
		obj.put("project", map);

		obj.put(SUMARY, Summary);
		obj.put(DESCRIPTION, Description);
		obj.put(ACTIVESPRINT_CUSTOMID, ActiveSprint);
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
