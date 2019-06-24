package com.jiraBug.testcases;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiraBug.JSONConverter.convertIntoJson;
import com.jiraBug.configreader.GlobalReader;
import com.jiraBug.constants.StatusCode;
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
	String key;
	String summary;
	String description;
	String ActiveSprint;
	String StepsToProduce;
	String ActualResult;
	String ExpectedResult;
	String IssueType;
	String BugPriority;
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

	@Test
	public void bugLockingJIRA() throws JsonProcessingException, IOException {
		json=intoJson.JsonConverter(key,summary,description ,ActiveSprint,StepsToProduce,ActualResult,ExpectedResult,IssueType,BugPriority);
		System.out.println(json);
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
