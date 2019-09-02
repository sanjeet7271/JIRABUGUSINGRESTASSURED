package com.jiraBug.testcases;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiraBug.JSONConverter.convertIntoJsonForTask;
import com.jiraBug.configreader.GlobalReader;
import com.jiraBug.constants.StatusCode;
import com.jiraBug.excel.ExcelSheetReader;
import com.jiraBug.restassured.RestAssuredClient;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

public class Task extends GlobalReader {
	RestAssuredClient restAssuredClient;
	ObjectMapper mapper;
	String endPointURLforTask;
	int responseCode;
	JsonNode taskJson;
	Response response;
	convertIntoJsonForTask intoJson;
	String xlFilePath = "./src/main/resources/jsonData/Jira.xlsx";
	String sheetName = "TaskDetails";
	ExcelSheetReader ProvideData=new ExcelSheetReader();
	Object[][] data;
	
	public Task() throws IOException {
		super();
	}

	@BeforeMethod
	public void setUp() throws IOException {
		restAssuredClient = new RestAssuredClient();
		mapper = new ObjectMapper();
		intoJson=new convertIntoJsonForTask();
		endPointURLforTask = this.endPointURL;

		System.out.println(endPointURLforTask);

	}
	@DataProvider(name = "task")
	public Object[][] taskData() throws Exception {
		data = ProvideData.testData(xlFilePath, sheetName);
        return data;
	}
	@Test(dataProvider="task")
	public void taskCreateJIRA(String Key,String Summary,String Description,String ActiveSprint, String IssueType,String BugPriority) throws JsonProcessingException, IOException {
		JSONObject json=intoJson.JsonConverter(Key,Summary,Description ,ActiveSprint,IssueType,BugPriority);
		System.out.println(json);
		response = restAssuredClient.requestPostCall(endPointURLforTask, json.toString());
		responseCode = response.getStatusCode();
		responseCode = response.getStatusCode();
		Assert.assertEquals(responseCode, StatusCode.RESPONSE_STATUS_CODE_201);
		String id = response.jsonPath().get("id");
		logger.info("New Bug Id :" + id);
		RestAssured.defaultParser = Parser.JSON;
		String key = response.jsonPath().get("key");
		logger.info("New Key :" + key);

	}
}
