package api.testedData;

import api.config.UserConfig;
import api.payloads.AuthPayload;
import org.aeonbits.owner.ConfigFactory;

public class TestedDataForAuthorization {

    AuthPayload authGuestPayload = new AuthPayload();
    UserConfig config = ConfigFactory.create(UserConfig.class);

    public AuthPayload testDataForAuthAsGuest() {
        return authGuestPayload.grant_type("client_credentials")
                .scope("guest:default");
    }

    public AuthPayload testDataForAuthAsPlayer(String username) {
        return authGuestPayload.grant_type("password")
                .username(username)
                .password(config.password());
    }
}


