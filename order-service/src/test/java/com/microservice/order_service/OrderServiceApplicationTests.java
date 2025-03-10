package com.microservice.order_service;
import com.microservice.order_service.stubs.InventoryClientStubs;
import io.restassured.RestAssured;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0") ;

	@LocalServerPort
	private Integer port ;

	@BeforeEach
	void init(){
		RestAssured.baseURI = "http://localhost" ;
		RestAssured.port = port ;
	}

	static {
		mySQLContainer.start();
	}

	@Test
	void placeOrderTest(){
		String requestBody = """
				{
				"skuCode" : "iphone_15" ,
				"price" : 10000 ,
				"quantity" : 1
				}
			""" ;

		InventoryClientStubs.stubInventoryCall("iphone_15" , 1);

		String responseBodyString = RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("api/order/placeorder")
				.then()
				.log().all()
				.statusCode(200)
				.extract()
				.body().asString() ;

		assertThat(responseBodyString, is("Order placed successfully"));
	}

}
