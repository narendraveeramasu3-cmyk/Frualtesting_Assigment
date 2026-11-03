
package base;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import org.testng.annotations.BeforeClass;

public class BaseApiTest {

    protected static final String BASE_URL =
            "https://restful-booker.herokuapp.com";

    @BeforeClass
    public void setup() {

        RestAssured.baseURI = BASE_URL;

        System.out.println("Restful-Booker API initialized!");
    }

    protected RequestSpecification request() {

        return RestAssured.given()
                .contentType("application/json")
                .accept("application/json");
    }
}

