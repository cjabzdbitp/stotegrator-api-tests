package tests;

import api.path.ApiService;
import api.payloads.AuthPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Tests for API part")
class ApiTests {

    static ApiService apiService = new ApiService();
    static AuthPayload authPayload = new AuthPayload();

    @Test
    @DisplayName("Return guest token")
    void test1() throws JsonProcessingException {
        Response response = apiService.generateToken(authPayload.createAuthGuestPayload());
        assertAll(
                () -> assertThat(response.getStatusCode(), is(200)),
                () -> assertThat(response.then().extract().path("access_token"), is(not(emptyString()))));
    }

    @Test
    @DisplayName("Creating player")
    void test2() throws JsonProcessingException {
        Response response = apiService.registerPlayer(authPayload.createPlayerPayload());
        assertAll(
                () -> assertThat(response.statusCode(), is(201)));
    }

    @Test
    @DisplayName("Authorization by player")
    void test3() throws JsonProcessingException {
        String username = apiService.registerPlayer(authPayload.createPlayerPayload()).then()
                .extract().path("username");

        Response response = apiService.generateToken(authPayload.createAuthPlayerPayload(username));
        assertAll(
                () -> assertThat(response.getStatusCode(), is(200)),
                () -> assertThat(response.jsonPath().get("access_token"), is(not(emptyString()))));
    }

    @Test
    @DisplayName("Return player info")
    void test4() throws JsonProcessingException {
        Response responsePlayerPayload = apiService.registerPlayer(authPayload.createPlayerPayload());
        int idPlayer = responsePlayerPayload.then().extract().path("id");
        String username = responsePlayerPayload.then().extract().path("username");

        String tokenPlayer = apiService.generateToken(authPayload.createAuthPlayerPayload(username))
                .then().extract().path("access_token");

        Response response = apiService.getPlayerInfo(idPlayer, tokenPlayer);
        assertAll(
                () -> assertThat(response.getStatusCode(), is(200)),
                () -> assertThat(response.jsonPath().get("id"), equalTo(idPlayer)));
    }

    @Test
    @DisplayName("Return player info by authorization another user")
    void test5() throws JsonProcessingException {
        Response responsePlayerPayload = apiService.registerPlayer(authPayload.createPlayerPayload());
        int idPlayer = responsePlayerPayload.then().extract().path(("id"));
        int wrongId = idPlayer + 1;
        String username = responsePlayerPayload.then().extract().path("username");

        String tokenPlayer = apiService.generateToken(authPayload.createAuthPlayerPayload(username))
                .then().extract().path("access_token");

        Response response = apiService.getPlayerInfo(wrongId, tokenPlayer);
        assertAll(
                () -> assertThat(response.getStatusCode(), is(404)));
    }
}

