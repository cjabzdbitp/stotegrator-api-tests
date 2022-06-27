package api.config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:user.properties"})
public interface UserConfig extends Config {

    @Key("username")
    String username();

    @Key("password")
    String password();
}
