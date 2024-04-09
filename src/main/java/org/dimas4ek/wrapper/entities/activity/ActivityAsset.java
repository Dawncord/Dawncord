package org.dimas4ek.wrapper.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.entities.image.ActivityImage;

public class ActivityAsset {
    private final JsonNode assets;
    private final String applicationId;
    private String largeImage;
    private String largeText;
    private String smallImage;
    private String smallText;

    public ActivityAsset(JsonNode assets, String applicationId) {
        this.assets = assets;
        this.applicationId = applicationId;
    }

    public ActivityImage getLargeImage() {
        if (largeImage == null) {
            largeImage = assets.get("large_image").asText();
        }
        return new ActivityImage(applicationId, largeImage);
    }

    public String getLargeText() {
        if (largeText == null) {
            largeText = assets.get("large_text").asText();
        }
        return largeText;
    }

    public ActivityImage getSmallImage() {
        if (smallImage == null) {
            smallImage = assets.get("small_image").asText();
        }
        return new ActivityImage(applicationId, smallImage);
    }

    public String getSmallText() {
        if (smallText == null) {
            smallText = assets.get("small_text").asText();
        }
        return smallText;
    }
}
