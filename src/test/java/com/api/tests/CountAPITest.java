package com.api.tests;

import static com.api.constants.Role.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

import io.restassured.response.Response;

public class CountAPITest {
	
	@Test(description="Verifying if Count api is giving correct response code for valid token",groups= {"api","regression","smoke"})

	public void verifyCountAPIResponse()
	{
		Response response =
		given()
		.spec(reqSpecWithAuth(FD))
		.when()
			.get("/dashboard/count")
		.then()
			.spec(responseSpec_OK())
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
	
	@Test(description="Verifying if count api is giving correct response code for invalid token",groups= {"api","negative","regression","smoke"})

	public void countAPITest_MissingAuthToken()
	{
		given()
		.spec(reqSpec())
		.and()
		.when()
		.get("/dashboard/count")
		.then()
		.spec(responseSpec_TEXT(401));
	}

}
