package io.github.dawncord.api.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.utils.LazyLoader;

/**
 * Represents secrets associated with an activity.
 * ActivitySecret is a class providing methods to access properties of activity secrets.
 */
public class ActivitySecret {
    private final LazyLoader loader;
    private String join;
    private String spectate;
    private String match;

    /**
     * Constructs an ActivitySecret object with the provided JSON node containing secret information.
     *
     * @param secrets The JSON node containing secret information.
     */
    public ActivitySecret(JsonNode secrets) {
        loader = new LazyLoader(secrets);
    }

    /**
     * Retrieves the secret for joining an activity.
     *
     * @return The join secret.
     */
    public String getJoinSecret() {
        join = loader.loadStringIfExists(join, "join");
        return join;
    }

    /**
     * Retrieves the secret for spectating an activity.
     *
     * @return The spectate secret.
     */
    public String getSpectateSecret() {
        spectate = loader.loadStringIfExists(spectate, "spectate");
        return spectate;
    }

    /**
     * Retrieves the secret for matching an activity.
     *
     * @return The match secret.
     */
    public String getMatchSecret() {
        match = loader.loadStringIfExists(match, "match");
        return match;
    }
}
