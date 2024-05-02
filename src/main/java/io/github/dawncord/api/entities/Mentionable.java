package io.github.dawncord.api.entities;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * A class representing a mentionable entity.
 */
public class Mentionable implements IMentionable {
    private final String value;
    private final JsonNode resolved;

    /**
     * Constructs a mentionable object with the specified value and resolved JSON node.
     *
     * @param value    The value of the mentionable entity.
     * @param resolved The resolved JSON node containing information about the entity.
     */
    public Mentionable(String value, JsonNode resolved) {
        this.value = value;
        this.resolved = resolved;
    }

    @Override
    public String getAsMention() {
        if (resolved != null) {
            JsonNode roles = resolved.get("roles");
            if (roles != null && roles.has(value)) {
                return "<@&" + value + ">";
            }

            JsonNode members = resolved.get("members");
            if (members != null && members.has(value)) {
                return "<@" + value + ">";
            }

            JsonNode channels = resolved.get("channels");
            if (channels != null && channels.has(value)) {
                return "<#" + value + ">";
            }
        }
        return null;
    }
}
