package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class LibraryApiTests extends BaseTest {
    @Test
    public void addBookAndVerify() {

                given()
                .contentType(ContentType.JSON)
                .body("{ \"title\": \"API Testing Book\", \"author\": \"Me\" }")

                .when()
                .post("/books")

                .then()
                .statusCode(201);
    }
    @Test
    public void addBookWithMissingFields(){

                given()
                .contentType("application/json")
                .body("{ \"title\": \"Error expected\" }")

                .when()
                .post("/books")

                .then()
                .statusCode(400)
                .body("error", containsString("author"));

    }
    @Test
    public void wrongEndpoint(){

        given()
                .contentType("application/json")
                .body("{ \"title\": \"API Testing Book\", \"author\": \"Me\" }")

                .when()
                .post("/boks")

                .then()
                .statusCode(404);

    }


    @Test
    public void editBookAndVerify() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"title\": \"Harry Potter\", \"author\": \"J.K. Rowling\" }")
                .when()
                .post("/books");

        response.prettyPrint();
        int id = response.jsonPath().getInt("id");

        given()
                .contentType(ContentType.JSON)
                .body("{ \"title\": \"Harry Potter and the Philosopher's Stone\", \"author\": \"J.K.Rowling\" }")

                .when()
                .put("/books/" + id)

                .then()
                .statusCode(200);
    }

    @Test
    public void editBookMissingId() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"title\": \"Harry Potter and the Philosopher's Stone\", \"author\": \"J.K.Rowling\" }")

                .when()
                .put("/books/9999")

                .then()
                .statusCode(404);
    }

    @Test
    public void getAllBooks() {
        given()
                .when()
                .get("/books")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", isA(java.util.List.class));
    }

    @Test
    public void deleteBookAndVerify() {
        int id = given()
                .contentType(ContentType.JSON)
                .body("{ \"title\": \"Delete\", \"author\": \"Delete\" }")
                .post("/books")
                .then()
                .statusCode(201)
                .extract().path("id");

        given()
                .when()
                .delete("/books/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    public void deleteBookNotExisting() {
        given()
                .when()
                .delete("/books/99999")
                .then()
                .statusCode(404);
    }

}
