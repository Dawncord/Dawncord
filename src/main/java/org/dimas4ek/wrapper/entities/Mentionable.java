package org.dimas4ek.wrapper.entities;

import org.json.JSONObject;

public class Mentionable implements IMentionable {
    private final String value;
    private final JSONObject resolved;

    public Mentionable(String value, JSONObject resolved) {
        this.value = value;
        this.resolved = resolved;
    }

    @Override
    public String getAsMention() {
        if (resolved.has("roles")) {
            for (String key : resolved.getJSONObject("roles").keySet()) {
                if (key.equals(value)) {
                    return "<@&" + value + ">";
                }
            }
        }
        if (resolved.has("members")) {
            for (String key : resolved.getJSONObject("members").keySet()) {
                if (key.equals(value)) {
                    return "<@" + value + ">";
                }
            }
        }
        return null;
    }
}
