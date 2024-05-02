package io.github.dawncord.api.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.image.ActivityImage;

/**
 * Represents assets associated with an activity.
 * ActivityAsset is a class providing methods to access various properties of activity assets.
 */
public class ActivityAsset {
    private final JsonNode assets;
    private final String applicationId;
    private String largeImage;
    private String largeText;
    private String smallImage;
    private String smallText;

    /**
     * Constructs an ActivityAsset object with the provided assets and application ID.
     *
     * @param assets        The JSON node containing assets information.
     * @param applicationId The ID of the application.
     */
    public ActivityAsset(JsonNode assets, String applicationId) {
        this.assets = assets;
        this.applicationId = applicationId;
    }

    /**
     * Retrieves the large image associated with the activity asset.
     *
     * @return The large image of the activity asset.
     */
    public ActivityImage getLargeImage() {
        if (largeImage == null) {
            largeImage = assets.get("large_image").asText();
        }
        return new ActivityImage(applicationId, largeImage);
    }

    /**
     * Retrieves the large text associated with the activity asset.
     *
     * @return The large text of the activity asset.
     */
    public String getLargeText() {
        if (largeText == null) {
            largeText = assets.get("large_text").asText();
        }
        return largeText;
    }

    /**
     * Retrieves the small image associated with the activity asset.
     *
     * @return The small image of the activity asset.
     */
    public ActivityImage getSmallImage() {
        if (smallImage == null) {
            smallImage = assets.get("small_image").asText();
        }
        return new ActivityImage(applicationId, smallImage);
    }

    /**
     * Retrieves the small text associated with the activity asset.
     *
     * @return The small text of the activity asset.
     */
    public String getSmallText() {
        if (smallText == null) {
            smallText = assets.get("small_text").asText();
        }
        return smallText;
    }
}
