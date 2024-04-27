package org.dimas4ek.dawncord.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;

public class ActivitySecret {
    private final JsonNode secrets;
    private String join;
    private String spectate;
    private String match;

    public ActivitySecret(JsonNode secrets) {
        this.secrets = secrets;
    }

    public String getJoin() {
        if (join == null) {
            join = secrets.get("join").asText();
        }
        return join;
    }

    public String getSpectate() {
        if (spectate == null) {
            spectate = secrets.get("spectate").asText();
        }
        return spectate;
    }

    public String getMatch() {
        if (match == null) {
            match = secrets.get("match").asText();
        }
        return match;
    }
}
