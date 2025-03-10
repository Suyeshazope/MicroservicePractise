package com.microservice.product_service;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.MongoDBContainer;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5") ;
	@LocalServerPort
	private Integer port ;


	@BeforeEach
	void setup(){
		RestAssured.baseURI = "http://localhost" ;
		RestAssured.port = port ;
	}

	static{
		mongoDBContainer.start();
	}

	@Test
	void shouldCreateProduct() {
		String requestBody = """
				{
				"name" : "Iphone 15" ,
				"description" : "Launched by apple" ,
				"prize" : 10000
				}
				""" ;

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/product/create")
				.then()
				.statusCode(201)
				.body("id" , notNullValue())
				.body("name" ,  equalTo("Iphone 15"))
				.body("description" , equalTo("Launched by apple"))
				.body("prize" , equalTo(10000)) ;

	}

}
