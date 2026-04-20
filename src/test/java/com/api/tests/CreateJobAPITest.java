package com.api.tests;

import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class CreateJobAPITest {

	@Test
	public void createJobAPITest()
	{
		Customer customer = new Customer("samdy", "knvs", "8484848888", "", "sandy@mail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("202", "SriNivas", "Lakshmi Vihar Street 3", "Beside Sai Baba Temple", "Nallagandla", "500019", "India", "Telangana");
		CustomerProduct customerProduct = new CustomerProduct("2025-04-06T18:30:00.000Z", "10030472358298", "10030472358298", "10030472358298", "2025-04-06T18:30:00.000Z", 1, 1);
		Problems problems = new Problems(1, "Battery Issue");
		Problems problemsArray[] = new Problems[1];
		problemsArray[0] = problems;
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemsArray);
		
		
		given()
		.spec(SpecUtil.reqSpecWithAuth(Role.FD, createJobPayload))
		.when()
		.post("/job/create")
		.then()
		.spec(SpecUtil.responseSpec_OK());
	}
}
