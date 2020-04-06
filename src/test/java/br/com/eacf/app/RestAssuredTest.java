package br.com.eacf.app;

import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class RestAssuredTest {

    @LocalServerPort
    private int port;


    /**
     * Verifies if the
     */
    @Test
    public void testMovieListSize() {

        given()
                .port(port)
                .when()
                .get("/movie/")
                .then()
                .assertThat()
                .statusCode(200)
                .body("size()", equalTo(196));
    }

    @Test
    public void testMovieNameById() {
        given()
                .port(port)
                .when()
                .get("/movie/7")
                .then()
                .assertThat()
                .statusCode(200)
                .body("title", Matchers.equalTo("Raise the Titanic"));
    }

    @Test
    public void testCorrectWinnerWithMin() {
        given()
                .port(port)
                .when()
                .get("/producer/winners/")
                .then()
                .statusCode(200)
                .body("min[0].producer", Matchers.equalTo("Joel Silver"));
    }

    @Test
    public void testCorrectWinnerWithMax() {
        given()
                .port(port)
                .when()
                .get("/producer/winners/")
                .then()
                .statusCode(200)
                .body("min.size()", Matchers.equalTo(1));
    }

    @Test
    public void testSaveNewMovieAndCheckId() {
        String requestBody = "{\n" +
                "        \"year\": 2000,\n" +
                "        \"title\": \"Added Movie\",\n" +
                "        \"winner\": false,\n" +
                "        \"studios\": [\n" +
                "            \"Added Productions\",\n" +
                "            \"Added Artists\"\n" +
                "        ],\n" +
                "        \"producers\": [\n" +
                "            \"Added Producer\"\n" +
                "        ]\n" +
                "    }";

        given()
                .port(port)
                .when()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/movie/")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", Matchers.equalTo(197))
                .body("title", Matchers.equalTo("Added Movie"))
                .body("studios.size()", Matchers.equalTo(2));

    }
}
