package io.github.dawncord.api.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Represents secrets associated with an activity.
 * ActivitySecret is a class providing methods to access properties of activity secrets.
 */
public class ActivitySecret {
    private final JsonNode secrets;
    private String join;
    private String spectate;
    private String match;

    /**
     * Constructs an ActivitySecret object with the provided JSON node containing secret information.
     *
     * @param secrets The JSON node containing secret information.
     */
    public ActivitySecret(JsonNode secrets) {
        this.secrets = secrets;
    }

    /**
     * Retrieves the secret for joining an activity.
     *
     * @return The join secret.
     */
    public String getJoin() {
        if (join == null) {
            join = secrets.get("join").asText();
        }
        return join;
    }

    /**
     * Retrieves the secret for spectating an activity.
     *
     * @return The spectate secret.
     */
    public String getSpectate() {
        if (spectate == null) {
            spectate = secrets.get("spectate").asText();
        }
        return spectate;
    }

    /**
     * Retrieves the secret for matching an activity.
     *
     * @return The match secret.
     */
    public String getMatch() {
        if (match == null) {
            match = secrets.get("match").asText();
        }
        return match;
    }
}
