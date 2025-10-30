package io.github.dawncord.api.entities.application;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.utils.LazyLoader;

public class IntegrationTypesConfig {
    private final LazyLoader loader;
    private final JsonNode config;
    private Oauth2InstallParams guildParam;
    private Oauth2InstallParams userParam;

    public IntegrationTypesConfig(JsonNode config) {
        this.config = config;
        loader = new LazyLoader(config);
    }

    public Oauth2InstallParams getGuildParam() {
        guildParam = loader.load(guildParam, () -> new Oauth2InstallParams(config.get("0").get("oauth2_install_params")));
        return guildParam;
    }

    public Oauth2InstallParams getUserParam() {
        userParam = loader.load(userParam, () -> new Oauth2InstallParams(config.get("1").get("oauth2_install_params")));
        return userParam;
    }

    public static class Oauth2InstallParams extends InstallParams {
        Oauth2InstallParams(JsonNode params) {
            super(params);
        }
    }
}