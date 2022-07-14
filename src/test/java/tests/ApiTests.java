package tests;

import api.path.ApiService;
import api.testedData.TestedDataForAuthorization;
import api.testedData.TestedDataForCreatePlayer;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Tests for API part")
class ApiTests {

    ApiService apiService = new ApiService();
    TestedDataForAuthorization testedDataAuth = new TestedDataForAuthorization();
    TestedDataForCreatePlayer testedDataPlayer = new TestedDataForCreatePlayer();

    @Test
    @DisplayName("Return guest token")
    void test1() {
        Response response = apiService.generateToken(testedDataAuth.testDataForAuthAsGuest());
        assertAll(
                () -> assertThat(response.getStatusCode(), is(200)),
                () -> assertThat(response.then().extract().path("access_token"), is(not(emptyString()))));
    }

    @Test
    @DisplayName("Creating player")
    void test2() {
        Response response = apiService.registerPlayer(testedDataPlayer.testDataForCreatePlayer());
        assertAll(
                () -> assertThat(response.statusCode(), is(201)));
    }

    @Test
    @DisplayName("Authorization by player")
    void test3() {
        String username = apiService.registerPlayer(testedDataPlayer.testDataForCreatePlayer()).then()
                .extract().path("username");

        Response response = apiService.generateToken(testedDataAuth.testDataForAuthAsPlayer(username));
        assertAll(
                () -> assertThat(response.getStatusCode(), is(200)),
                () -> assertThat(response.jsonPath().get("access_token"), is(not(emptyString()))));
    }

    @Test
    @DisplayName("Return player info")
    void test4() {
        Response responsePlayerPayload = apiService.registerPlayer(testedDataPlayer.testDataForCreatePlayer());
        int idPlayer = responsePlayerPayload.then().extract().path("id");
        String username = responsePlayerPayload.then().extract().path("username");

        String tokenPlayer = apiService.generateToken(testedDataAuth.testDataForAuthAsPlayer(username))
                .then().extract().path("access_token");

        Response response = apiService.getPlayerInfo(idPlayer, tokenPlayer);
        assertAll(
                () -> assertThat(response.getStatusCode(), is(200)),
                () -> assertThat(response.jsonPath().get("id"), equalTo(idPlayer)));
    }

    @Test
    @DisplayName("Return player info by authorization another user")
    void test5() {
        Response responsePlayerPayload = apiService.registerPlayer(testedDataPlayer.testDataForCreatePlayer());
        int idPlayer = responsePlayerPayload.then().extract().path(("id"));
        int wrongId = idPlayer + 1;
        String username = responsePlayerPayload.then().extract().path("username");

        String tokenPlayer = apiService.generateToken(testedDataAuth.testDataForAuthAsPlayer(username))
                .then().extract().path("access_token");

        Response response = apiService.getPlayerInfo(wrongId, tokenPlayer);
        assertAll(
                () -> assertThat(response.getStatusCode(), is(404)));
    }
}

