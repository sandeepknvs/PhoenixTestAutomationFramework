package com.api.tests;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.api.constants.Role.*;
import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class MasterAPITest {
	
	@Test(description="Verifying if master api is giving correct response ",groups= {"api","regression","smoke"})
	public void masterAPITest()
	{
		
		given()
		.spec(reqSpecWithAuth(FD))
		.when()
		.post("/master")
		.then()
		.spec(responseSpec_OK())
		.body("message",Matchers.equalTo("Success"))
		.body("data",Matchers.notNullValue())
		.body("data", Matchers.hasKey("mst_oem"))
		.body("$", Matchers.hasKey("message"))
		.body("$", Matchers.hasKey("data"))
		.body("data.mst_oem.size()",Matchers.greaterThanOrEqualTo(0))
		.body("data.mst_model.size()",Matchers.greaterThanOrEqualTo(0))
		.body("data.mst_oem.id",Matchers.everyItem(Matchers.notNullValue()))
		.body("data.mst_oem.name",Matchers.everyItem(Matchers.notNullValue()))
		.body(matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));
		
	}
	@Test(description="Verifying if master api is giving correct response code for invalid token",groups= {"api","negative","regression","smoke"})

	public void invalidTokenMasterAPITest()
	{
		given()
		.spec(reqSpec())
		.when()
		.post("/master")
		.then()
		.spec(responseSpec_TEXT(401));
	}
}
