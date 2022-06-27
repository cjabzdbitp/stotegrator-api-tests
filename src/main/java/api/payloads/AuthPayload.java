package api.payloads;

import api.config.UserConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.aeonbits.owner.ConfigFactory;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AuthPayload {

    static Faker faker = new Faker(Locale.ENGLISH);
    static UserConfig config = ConfigFactory.create(UserConfig.class);

    public String createAuthGuestPayload() throws JsonProcessingException {
        Map<String, String> authPayload = new HashMap<>();
        authPayload.put("grant_type", "client_credentials");
        authPayload.put("scope", "guest:default");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(authPayload);
    }

    public String createPlayerPayload() throws JsonProcessingException {
        String name = faker.regexify("[a-z]{7,9}");
        Map<String, String> playerPayload = new HashMap<>();
        playerPayload.put("username", name);
        playerPayload.put("password_change", config.password());
        playerPayload.put("password_repeat", config.password());
        playerPayload.put("email", name + "@example.com");
        playerPayload.put("name", name);
        playerPayload.put("surname", name);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(playerPayload);
    }

    public String createAuthPlayerPayload(String username) throws JsonProcessingException {
        Map<String, String> authPayload = new HashMap<>();
        authPayload.put("grant_type", "password");
        authPayload.put("username", username);
        authPayload.put("password", config.password());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(authPayload);
    }
}