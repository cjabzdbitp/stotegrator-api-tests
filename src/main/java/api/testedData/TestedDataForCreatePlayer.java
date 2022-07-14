package api.testedData;

import api.config.UserConfig;
import api.payloads.PlayerPayload;
import com.github.javafaker.Faker;
import org.aeonbits.owner.ConfigFactory;

import java.util.Locale;

public class TestedDataForCreatePlayer {

    PlayerPayload playerPayload = new PlayerPayload();
    Faker faker = new Faker(Locale.ENGLISH);
    UserConfig config = ConfigFactory.create(UserConfig.class);

    public PlayerPayload testDataForCreatePlayer() {
        String name = faker.regexify("[a-z]{7,9}");
        return playerPayload.name(name)
                .username(name)
                .surname(name)
                .email(name + "@example.com")
                .password_change(config.password())
                .password_repeat(config.password());
    }
}




