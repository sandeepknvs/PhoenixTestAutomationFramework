package com.api.tests;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.constants.Role.*;

import static com.api.utils.AuthTokenProvider.*;

import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

public class UserDetailsAPIFDTest {
	
	@Test
	public void userDetailsAPIFDTest() throws IOException
	{

		Header authHeader = new Header("Authorization",getToken(FD));
		 given()
		 	.baseUri(getProperty("BASE_URI"))
		 	.and()
		 	.header(authHeader)
		 	.and()
		 	.accept(ContentType.JSON)
		 	.contentType(ContentType.JSON)
		 	.log().uri()
		 	.log().method()
		 	.log().body()
		 .when()
		 	.get("userdetails")
		 .then()
		 	.log().all()
		 	.statusCode(200)
		 	.time(lessThan(1500L))
		 	.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
	}
}
