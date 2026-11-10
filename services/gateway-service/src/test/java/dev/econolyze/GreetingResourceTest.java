package dev.econolyze;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.common.QuarkusTestResource;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(JwtTestResource.class)
class GreetingResourceTest {
    @Test
    void pingReturnsPong() {
        given().when().get("/api/ping")
                .then().statusCode(200).body(is("pong"));
    }

    @Test
    void financialEndpointRejectsAnonymousRequest() {
        given().when().get("/api/account")
                .then().statusCode(401);
    }
}
