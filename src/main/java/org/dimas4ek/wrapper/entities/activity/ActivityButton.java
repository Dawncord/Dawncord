package org.dimas4ek.wrapper.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;

public class ActivityButton {
    private final JsonNode buttons;
    private String label;
    private String url;

    public ActivityButton(JsonNode buttons) {
        this.buttons = buttons;
    }

    public String getLabel() {
        if (label == null) {
            label = buttons.get("label").asText();
        }
        return label;
    }

    public String getUrl() {
        if (url == null) {
            url = buttons.get("url").asText();
        }
        return url;
    }
}
