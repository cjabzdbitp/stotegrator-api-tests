package api.path;


import api.config.ProjectConfig;
import api.config.UserConfig;
import api.payloads.AuthPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ApiService {
    static ProjectConfig config = ConfigFactory.create(ProjectConfig.class);
    static UserConfig configUsers = ConfigFactory.create(UserConfig.class);
    static String baseURI = config.baseURI();
    static String username = configUsers.username();

    public Response generateToken(String authBodyRequest) {

        RequestSpecification request = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Basic " + Base64.getEncoder()
                        .encodeToString(username.getBytes(StandardCharsets.UTF_8)))
                .body(authBodyRequest);
        return request.post(baseURI + "/v2/oauth2/token");
    }

    public String getGuestToken() throws JsonProcessingException {
        AuthPayload authPayload = new AuthPayload();
        return generateToken(authPayload.createAuthGuestPayload()).jsonPath().getString("access_token");
    }

    public Response registerPlayer(String playerPayload) throws JsonProcessingException {

        RequestSpecification request = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + getGuestToken())
                .body(playerPayload);
        return request.post(baseURI + "/v2/players");
    }

    public Response getPlayerInfo(int playerId, String token) {

        RequestSpecification request = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token);
        return request.get(baseURI + "/v2/players/" + playerId);
    }
}
