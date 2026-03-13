package com.api.tests;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;
import static com.api.utils.ConfigManager.*;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class loginAPITest {
	
	@Test
	public void loginAPITest() throws IOException
	{
		
		UserCredentials userCredentials = new UserCredentials("iamfd","password");
		
		Response response = 
		given()
			.baseUri(getProperty("BASE_URI"))
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
	