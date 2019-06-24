package com.jiraBug.testcases;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiraBug.JSONConverter.convertIntoJsonForTask;
import com.jiraBug.configreader.GlobalReaderForTask;
import com.jiraBug.constants.StatusCode;
import com.jiraBug.restassured.RestAssuredClient;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

public class Task extends GlobalReaderForTask {
	RestAssuredClient restAssuredClient;
	ObjectMapper mapper;
	String endPointURLforLogin;
	int responseCode;
	JsonNode taskJson;
	Response response;
	Object json;
	convertIntoJsonForTask intoJson;
	public Task() throws IOException {
		super();
	}

	@BeforeMethod
	public void setUp() throws IOException {
		restAssuredClient = new RestAssuredClient();
		mapper = new ObjectMapper();
		intoJson=new convertIntoJsonForTask();
		endPointURLforLogin = this.endPointURL;

		System.out.println(endPointURLforLogin);

	}

	@Test
	public void taskInJIRA() throws JsonProcessingException, IOException {
		json=intoJson.JsonConverter();
		System.out.println(json.toString());
		response = restAssuredClient.requestPostCall(endPointURLforLogin, json.toString());
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
