package com.jiraBug.testcases;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiraBug.JSONConverter.convertIntoJson;
import com.jiraBug.configreader.GlobalReader;
import com.jiraBug.constants.StatusCode;
import com.jiraBug.excel.ExcelSheetReader;
import com.jiraBug.restassured.RestAssuredClient;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

public class Bug extends GlobalReader {
	RestAssuredClient restAssuredClient;
	ObjectMapper mapper;
	String endPointURLforLogin;
	int responseCode;
	JsonNode bugJson;
	Response response;
	String json;
	convertIntoJson intoJson;
	String xlFilePath = "./src/main/resources/jsonData/Jira.xlsx";
	String sheetName = "BugDetails";
	ExcelSheetReader ProvideData=new ExcelSheetReader();
	Object[][] data;
	
	public Bug() throws IOException {
		super();
	}

	@BeforeMethod
	public void setUp() throws IOException {
		restAssuredClient = new RestAssuredClient();
		mapper = new ObjectMapper();
		intoJson=new convertIntoJson();
		endPointURLforLogin = this.endPointURL;

		System.out.println(endPointURLforLogin);

	}

	@DataProvider(name = "bug")
	public Object[][] CredentialtoLogin() throws Exception {
		data = ProvideData.testData(xlFilePath, sheetName);
        return data;
	}
	@Test(dataProvider="bug")
	public void bugLockingJIRA(String Key,String Summary,String Description,String ActiveSprint, String StepsToProduce,String ActualResult,String ExpectedResult,String IssueType,String BugPriority) throws JsonProcessingException, IOException{
		String json=intoJson.JsonConverter(Key,Summary,Description ,ActiveSprint,StepsToProduce,ActualResult,ExpectedResult,IssueType,BugPriority);
		response = restAssuredClient.requestPostCall(endPointURLforLogin, json);
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
