package com.api.utils;

import com.api.constants.Role;
import com.api.request.model.UserCredentials;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;

import static io.restassured.matcher.RestAssuredMatchers.*;
public class AuthTokenProvider {
	
	private AuthTokenProvider()
	{
		
	}

	public static String getToken(Role role) {
		
		UserCredentials userCredentials = null;
		if(role == Role.FD)
		{
			userCredentials = new UserCredentials("iamfd","password");
		}
		else if(role == Role.SUP)
		{
			userCredentials = new UserCredentials("iamsup","password");
		}
		else if(role == Role.ENG)
		{
			userCredentials = new UserCredentials("iameng","password");
		}
		else if(role == Role.QC)
		{
			userCredentials = new UserCredentials("iamqc","password");
		}
		
		String token = 
				given()
				.baseUri(ConfigManager.getProperty("BASE_URI"))
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(userCredentials)
				.when()
				.post("login")
				.then()
				.log().ifValidationFails()
				.statusCode(200)
				.body("message", Matchers.equalTo("Success"))
				.extract().response()
				.body().jsonPath().getString("data.token");
		
		//System.out.print("----------");
		return token;

	}

}
