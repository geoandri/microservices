package org.geoandri.teams.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.restassured.http.ContentType;
import org.geoandri.teams.dto.TeamDto;
import org.geoandri.teams.event.EventType;
import org.geoandri.teams.event.TeamEvent;
import org.geoandri.teams.exception.error.ErrorMessage;
import org.geoandri.teams.producer.TeamProducer;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TeamResourceTest {

    @InjectSpy
    TeamProducer teamProducer;

    @Test
    @Order(1)
    public void testGetAllTeamsEndpoint() {
        given()
                .when().get("/teams")
                .then()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    @Order(2)
    public void testGetAllTeamsEndpointWithPagination() {
        TeamDto[] response = given()
                                    .when().get("/teams?pageNum=2&pageSize=1")
                                    .then()
                                    .statusCode(200)
                                    .extract().body().as(TeamDto[].class);

        assertEquals("Team B", response[0].getName());
    }

    @Test
    @Order(3)
    public void testGetTeamEndpoint() {
        given()
                .when().get("/teams/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("Team A"))
                .body("description", equalTo("Team A description"));
    }

    @Test
    @Order(4)
    public void testPostTeamEndpoint() {
        TeamDto teamDto = new TeamDto();
        teamDto.setName("A test team");
        teamDto.setDescription("A test description");

        TeamDto responseTeamDto = given()
                                        .contentType(ContentType.JSON)
                                        .with().body(teamDto)
                                        .when().post("/teams")
                                        .then()
                                        .contentType(ContentType.JSON)
                                        .statusCode(201)
                                        .extract().body().as(TeamDto.class);

        assertEquals("A test team", responseTeamDto.getName());
        assertEquals("A test description", responseTeamDto.getDescription());

        Mockito.verify(teamProducer, Mockito.times(1)).
                publishEvent(new TeamEvent(EventType.TEAM_CREATED, responseTeamDto.getId()));
    }

    @Test
    @Order(5)
    public void testPostTeamEndpointOnFailure() {
        TeamDto teamDto = new TeamDto();

        ErrorMessage response = given()
                                        .contentType(ContentType.JSON)
                                        .with().body(teamDto)
                                        .when().post("/teams")
                                        .then()
                                        .contentType(ContentType.JSON)
                                        .statusCode(400)
                                        .extract().body().as(ErrorMessage.class);

        assertEquals(1, response.getErrors().size());
        assertTrue(response.getErrors().contains("Team's name is required."));

        Mockito.verify(teamProducer, Mockito.never())
                .publishEvent(Mockito.any(TeamEvent.class));
    }

    @Test
    @Order(6)
    public void testPutTeamEndpoint() {
        TeamDto teamDto = new TeamDto();
        teamDto.setName("An updated team");
        teamDto.setDescription("An updated description");

        TeamDto responseTeamDto = given()
                                        .contentType(ContentType.JSON)
                                        .with().body(teamDto)
                                        .when().put("/teams/1")
                                        .then()
                                        .contentType(ContentType.JSON)
                                        .statusCode(200)
                                        .extract().body().as(TeamDto.class);

        assertEquals("An updated team", responseTeamDto.getName());
        assertEquals("An updated description", responseTeamDto.getDescription());
    }

    @Test
    @Order(7)
    public void testPutTeamEndpointOnFailure() {
        TeamDto teamDto = new TeamDto();
        teamDto.setName("An updated team");
        teamDto.setDescription("An updated description");

        ErrorMessage response = given()
                                        .contentType(ContentType.JSON)
                                        .with().body(teamDto)
                                        .when().put("/teams/100")
                                        .then()
                                        .contentType(ContentType.JSON)
                                        .statusCode(404)
                                        .extract().body().as(ErrorMessage.class);

        assertEquals("Team with id 100 could not be found.", response.getError());

        Mockito.verify(teamProducer, Mockito.never())
                .publishEvent(Mockito.any(TeamEvent.class));
    }

    @Test
    @Order(8)
    public void testDeleteTeam() {
        given()
                .contentType(ContentType.JSON)
                .when().delete("/teams/3")
                .then()
                .statusCode(200);

        Mockito.verify(teamProducer, Mockito.times(1))
                .publishEvent(new TeamEvent(EventType.TEAM_DELETED, 3));
    }

    @Test
    @Order(9)
    public void testDeleteTeamOnError() {
        given()
                .contentType(ContentType.JSON)
                .when().delete("/teams/30")
                .then()
                .statusCode(404);

        Mockito.verify(teamProducer, Mockito.never())
                .publishEvent(Mockito.any(TeamEvent.class));
    }

    @Test
    @Order(10)
    public void testUpdateTeamWithDuplicateName() {
        TeamDto teamDto = new TeamDto();
        teamDto.setName("Team B");
        teamDto.setDescription("An updated description");

        given()
                .contentType(ContentType.JSON)
                .with().body(teamDto)
                .when().put("/teams/1")
                .then()
                .statusCode(400);

        Mockito.verify(teamProducer, Mockito.never())
                .publishEvent(Mockito.any(TeamEvent.class));
    }

    @Test
    @Order(11)
    public void testPostTeamWithDuplicateName() {
        TeamDto teamDto = new TeamDto();
        teamDto.setName("Team B");
        teamDto.setDescription("A description");

        given()
                .contentType(ContentType.JSON)
                .with().body(teamDto)
                .when().post("/teams/")
                .then()
                .statusCode(400);

        Mockito.verify(teamProducer, Mockito.never())
                .publishEvent(Mockito.any(TeamEvent.class));
    }
}
