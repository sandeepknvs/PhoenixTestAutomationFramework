package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class CreateJobAPITest {

	@Test
	public void createJobAPITest()
	{
		Customer customer = new Customer("samdy", "knvs", "8484848888", "", "sandy@mail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("202", "SriNivas", "Lakshmi Vihar Street 3", "Beside Sai Baba Temple", "Nallagandla", "500019", "India", "Telangana");
		CustomerProduct customerProduct = new CustomerProduct("2025-04-06T18:30:00.000Z", "21130472357289", "21130472357289", "21130472357289", "2025-04-06T18:30:00.000Z", 1, 1);
		Problems problems = new Problems(1, "Battery Issue");
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemsList);
		
		
		given()
		.spec(SpecUtil.reqSpecWithAuth(Role.FD, createJobPayload))
		.when()
		.post("/job/create")
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
		.body("message", Matchers.equalTo("Job created successfully. "))
		.body("data.mst_service_location_id", Matchers.equalTo(1))
		.body("data.job_number", Matchers.startsWithIgnoringCase("JOB_"));
	}
}
