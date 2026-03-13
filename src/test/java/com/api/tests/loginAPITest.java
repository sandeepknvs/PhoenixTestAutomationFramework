package com.api.tests;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class loginAPITest {
	
	@Test
	public void loginAPITest()
	{
		
		UserCredentials userCredentials = new UserCredentials("iamfd","password");
		baseURI = "http://64.227.160.186:9000/v1/";
		Response response = 
		given()
			.baseUri("http://64.227.160.186:9000/v1/")
			.and()
			.contentType(JSON)
			.and()
			.accept(JSON)
			.log().uri()
			.log().method()
			.log().body()
			.and()
			.body(userCredentials)
		.when()
			.post("login")
		.then()
			.log().all()
			.and()
			.statusCode(200)
			.and()
			.body("data.token", notNullValue())
			.and()
			.body("message", equalTo("Success"))
			.and()
			.time(lessThan(1500L))
			.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"))
			.extract().response();
			
			
			
			
	}

}
	