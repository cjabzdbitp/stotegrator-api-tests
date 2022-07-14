package api.path;


import api.config.ProjectConfig;
import api.config.UserConfig;
import api.payloads.AuthPayload;
import api.payloads.PlayerPayload;
import api.testedData.TestedDataForAuthorization;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ApiService {

    ProjectConfig config = ConfigFactory.create(ProjectConfig.class);
    UserConfig configUsers = ConfigFactory.create(UserConfig.class);
    String baseURI = config.baseURI();
    String username = configUsers.username();

    public Response generateToken(AuthPayload authBodyRequest) {
        RequestSpecification request = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Basic " + Base64.getEncoder()
                        .encodeToString(username.getBytes(StandardCharsets.UTF_8)))
                .body(authBodyRequest);
        return request.post(baseURI + "/v2/oauth2/token");
    }

    public String getGuestToken() {
        TestedDataForAuthorization testedDataForAuthorization = new TestedDataForAuthorization();
        return generateToken(testedDataForAuthorization.testDataForAuthAsGuest()).jsonPath().getString("access_token");
    }

    public Response registerPlayer(PlayerPayload playerPayload) {
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
