package br.com.eacf.app;

import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestAssuredTest {

    @LocalServerPort
    private int port;


    /**
     * Verifies if the
     */
    @Test
    @Order(0)
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
    @Order(1)
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
    @Order(2)
    public void testCorrectWinnerWithMin() {
        given()
                .port(port)
                .when()
                .get("/producer/")
                .then()
                .statusCode(200)
                .body("min[0].producer", Matchers.equalTo("Joel Silver"));
    }

    @Test
    @Order(3)
    public void testCorrectWinnerWithMax() {
        given()
                .port(port)
                .when()
                .get("/producer/")
                .then()
                .statusCode(200)
                .body("min.size()", Matchers.equalTo(1));
    }

    @Test
    @Order(4)
    public void testSaveNewMovie() {
        String requestBody = "{\n" +
                "        \"year\": 2064,\n" +
                "        \"title\": \"Test\",\n" +
                "        \"winner\": true,\n" +
                "        \"studios\": [\n" +
                "            \"Test\"\n" +
                "        ],\n" +
                "        \"producers\": [\n" +
                "            \"Matthew Vaughn\"\n" +
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
                .body("year", Matchers.equalTo(2064))
                .body("title", Matchers.equalTo("Test"))
                .body("studios[0]", Matchers.equalTo("Test"))
                .body("producers[0]", Matchers.equalTo("Matthew Vaughn"));

    }

    @Test
    @Order(5)
    public void checkNewMaxProducerWithNewRegistry() {

        given()
                .port(port)
                .when()
                .get("/producer/")
                .then()
                .statusCode(200)
                .body("max[0].producer", Matchers.equalTo("Matthew Vaughn"))
                .body("max[0].interval", Matchers.equalTo(49))
                .body("max[0].previousWin", Matchers.equalTo(2015))
                .body("max[0].followingWin", Matchers.equalTo(2064));

    }
}
