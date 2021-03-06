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
public class AuthPayload {

    @JsonProperty("grant_type")
    private String grant_type;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}