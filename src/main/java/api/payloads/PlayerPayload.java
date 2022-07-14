package api.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Accessors(fluent = true)
public class PlayerPayload {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password_change")
    private String password_change;

    @JsonProperty("password_repeat")
    private String password_repeat;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;
}

