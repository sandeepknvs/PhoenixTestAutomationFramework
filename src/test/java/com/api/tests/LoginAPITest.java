package com.api.tests;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;
import com.api.utils.SpecUtil;

import static com.api.utils.ConfigManager.*;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class LoginAPITest {
	
	@Test
	public void loginAPITest() throws IOException
	{
		
		UserCredentials userCredentials = new UserCredentials("iamfd","password");
		
		Response response = 
		given()
			.spec(SpecUtil.reqSpec(userCredentials))
			
		.when()
			.post("login")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.and()
			.body("data.token", notNullValue())
			.and()
			.body("message", equalTo("Success"))
			
			.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"))
			.extract().response();
			
			
			
			
	}

}
	