package com.api.tests;
import static com.api.constants.Role.FD;
import static com.api.utils.AuthTokenProvider.getToken;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

import io.restassured.http.Header;

public class UserDetailsAPIFDTest {
	
	@Test(description="Verify if the userdetails API response is shown correctly" ,groups= {"api","smoke", "regression"})
	public void userDetailsAPIFDTest() throws IOException
	{

		Header authHeader = new Header("Authorization",getToken(FD));
		 given()
		 	.spec(SpecUtil.reqSpecWithAuth(FD))
		 .when()
		 	.get("userdetails")
		 .then()
		 	.spec(SpecUtil.responseSpec_OK())
		 	.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
	}
}
