package io.github.dawncord.api.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.utils.LazyLoader;

/**
 * Represents a button associated with an activity.
 * ActivityButton is a class providing methods to access properties of activity buttons.
 */
public class ActivityButton {
    private final LazyLoader loader;
    private String label;
    private String url;

    /**
     * Constructs an ActivityButton object with the provided JSON node containing button information.
     *
     * @param buttons The JSON node containing button information.
     */
    public ActivityButton(JsonNode buttons) {
        loader = new LazyLoader(buttons);
    }

    /**
     * Retrieves the label of the activity button.
     *
     * @return The label of the activity button.
     */
    public String getLabel() {
        label = loader.loadStringIfNull(label, "label");
        return label;
    }

    /**
     * Retrieves the URL associated with the activity button.
     *
     * @return The URL of the activity button.
     */
    public String getUrl() {
        url = loader.loadStringIfNull(url, "url");
        return url;
    }
}
