package org.geoandri.developers.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.geoandri.developers.dto.DeveloperDto;
import org.geoandri.developers.exception.error.ErrorMessage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeveloperResourceTest {


    @Test
    @Order(1)
    public void testGetAllDevelopersEndpoint() {
        given()
                .when().get("/developers")
                .then()
                .statusCode(200)
                .body("size()", is(6));
    }

    @Test
    @Order(2)
    public void testGetAllDevelopersEndpointWithPagination() {
        DeveloperDto[] response = given()
                .when().get("/developers?pageNum=2&pageSize=2")
                .then()
                .statusCode(200)
                .extract().body().as(DeveloperDto[].class);

        assertEquals("Developer 2A", response[0].getName());
        assertEquals("Developer 2B", response[1].getName());
    }

    @Test
    @Order(3)
    public void testGetDeveloperEndpoint() {
        given()
                .when().get("/developers/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("Developer 1A"))
                .body("team", equalTo("Team A"));
    }

    @Test
    @Order(4)
    public void testPostDeveloperEndpoint() {
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setName("A new developer");
        developerDto.setTeam("Team A");

        given()
                .contentType(ContentType.JSON)
                .with().body(developerDto)
                .when().post("/developers")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(201)
                .body("name", equalTo("A new developer"))
                .body("team", equalTo("Team A"));
    }

    @Test
    @Order(5)
    public void testPutDeveloperEndpoint() {
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setName("An updated developer");
        developerDto.setTeam("Team B");

        given()
                .contentType(ContentType.JSON)
                .with().body(developerDto)
                .when().put("/developers/1")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("name", equalTo("An updated developer"))
                .body("team", equalTo("Team B"));
    }


    @Test
    @Order(6)
    public void testDeleteDeveloperEndpoint() {
        given()
                .contentType(ContentType.JSON)
                .when().delete("/developers/1")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(7)
    public void testPostDeveloperEndpointWhenValidationFails() {
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setTeam("Team A");

        ErrorMessage response = given()
                .contentType(ContentType.JSON)
                .with().body(developerDto)
                .when().post("/developers")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(400)
                .extract().body().as(ErrorMessage.class);

        assertEquals(1, response.getErrors().size());
        assertTrue(response.getErrors().contains("Developer's name is required."));
    }
}
