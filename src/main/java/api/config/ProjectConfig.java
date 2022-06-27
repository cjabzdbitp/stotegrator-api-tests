package api.config;

import org.aeonbits.owner.Config;

import static org.aeonbits.owner.Config.*;


@Sources({"classpath:config.properties"})
public interface ProjectConfig extends Config {

    @Key("baseURI")
    String baseURI();
}
