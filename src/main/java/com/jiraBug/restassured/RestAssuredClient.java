package com.jiraBug.restassured;

import java.io.IOException;

import com.jiraBug.configreader.GlobalReader;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestAssuredClient extends GlobalReader {
	String username = prop.getProperty("username");
	String password = prop.getProperty("password");

	public RestAssuredClient() throws IOException {
		super();
	}

	/**
	 * Post Call
	 * 
	 * @param url
	 * @param entityString
	 * @return
	 */
	public Response requestPostCall(String url, String entityString) {
		Response response = RestAssured.given().auth().preemptive().basic(username, password).body(entityString)
				.contentType(ContentType.JSON).log().all().when().post(url).then().log().all().extract().response();
		return response;

	}

	/**
	 * PUT call
	 * 
	 * @param url
	 * @param entityString
	 * @return
	 */
	public Response requestPutCall(String url, String entityString) {
		Response response = RestAssured.given().body(entityString).contentType(ContentType.JSON).log().all().when()
				.put(url).then().log().all().extract().response();
		return response;
	}

}
