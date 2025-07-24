package api;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class LibraryApiTests extends BaseTest {
    @Test
    public void loginWithValidCredentials() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"username\": \"admin\", \"password\": \"1234\" }")
                .when()
                .post("/login")
                .then()
                .statusCode(200);
    }
    @Test
    public void testLoginWithInvalidCredentials() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"username\": \"admin\", \"password\": \"wrong\" }")
                .when()
                .post("/login")
                .then()
                .statusCode(401);
    }
}
