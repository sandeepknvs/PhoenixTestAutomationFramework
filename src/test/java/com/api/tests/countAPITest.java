package com.api.tests;

import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static com.api.constants.Role.*;

import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;

import static io.restassured.http.ContentType.*;

import static com.api.utils.ConfigManager.*;

import static io.restassured.RestAssured.*;

public class countAPITest {
	
	@Test
	public void verifyCountAPIResponse()
	{
		Response response =
		given()
			.baseUri(getProperty("BASE_URI"))
			.and()
			.contentType(JSON)
			.and()
			.accept(JSON)
			.and()
			.header("Authorization",getToken(FD))
			.log().uri()
			.log().method()
			.log().headers()
		.when()
			.get("/dashboard/count")
		.then()
			.log().all()
			.and()
			.statusCode(200)
			.and()
			.time(lessThan(1000l))
			.and()
			.body("message", equalTo("Success"))
			.and()
			.body("data", Matchers.notNullValue())
			.and()
			.body("data.size()", Matchers.equalTo(3))
			.and()
			.body("data.count", Matchers.everyItem(Matchers.greaterThanOrEqualTo(0)))
			.and()
			.body("data.label", Matchers.everyItem(Matchers.not(Matchers.blankOrNullString())))
			.and()
			.body("data.key", Matchers.containsInAnyOrder("created_today","pending_fst_assignment","pending_for_delivery"))
			.and()
			.body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema.json"))
			.extract().response();
	}
	
	@Test
	public void countAPITest_MissingAuthToken()
	{
		given()
		.baseUri(getProperty("BASE_URI"))
		.and()
		.when()
		.get("/dashboard/count")
		.then()
		.log().all()
		.statusCode(401);
	}

}
