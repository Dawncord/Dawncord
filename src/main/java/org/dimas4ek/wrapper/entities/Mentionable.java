package org.dimas4ek.wrapper.entities;

import com.fasterxml.jackson.databind.JsonNode;

public class Mentionable implements IMentionable {
    private final String value;
    private final JsonNode resolved;

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
