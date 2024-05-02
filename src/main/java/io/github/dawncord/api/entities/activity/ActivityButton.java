package io.github.dawncord.api.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Represents a button associated with an activity.
 * ActivityButton is a class providing methods to access properties of activity buttons.
 */
public class ActivityButton {
    private final JsonNode buttons;
    private String label;
    private String url;

    /**
     * Constructs an ActivityButton object with the provided JSON node containing button information.
     *
     * @param buttons The JSON node containing button information.
     */
    public ActivityButton(JsonNode buttons) {
        this.buttons = buttons;
    }

    /**
     * Retrieves the label of the activity button.
     *
     * @return The label of the activity button.
     */
    public String getLabel() {
        if (label == null) {
            label = buttons.get("label").asText();
        }
        return label;
    }

    /**
     * Retrieves the URL associated with the activity button.
     *
     * @return The URL of the activity button.
     */
    public String getUrl() {
        if (url == null) {
            url = buttons.get("url").asText();
        }
        return url;
    }
}
