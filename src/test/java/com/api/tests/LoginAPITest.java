package com.api.tests;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.utils.SpecUtil;

import static com.api.utils.ConfigManager.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;
import io.restassured.response.Response;

public class LoginAPITest {
	
	private UserCredentials userCredentials;
	@BeforeMethod(description="Create the payload for the Login API")
	public void setup()
	{
		userCredentials = new UserCredentials("iamfd","password");
	}
	
	
	
	@Test(description = "Verifying if Login API is working fine for FD User", groups= {"api","regression","smoke"})
	public void loginAPITest() throws IOException
	{
		
		UserCredentials userCredentials = new UserCredentials("iamfd","password");
		 
		given()
			.spec(SpecUtil.reqSpec(userCredentials))
			
		.when()
			.post("login")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body("message", equalTo("Success"))
			.and()
			.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
					
	}

}
	