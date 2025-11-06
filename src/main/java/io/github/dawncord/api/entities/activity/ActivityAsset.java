package io.github.dawncord.api.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.image.ActivityImage;
import io.github.dawncord.api.utils.LazyLoader;

/**
 * Represents assets associated with an activity.
 * ActivityAsset is a class providing methods to access various properties of activity assets.
 */
public class ActivityAsset {
    private final LazyLoader loader;
    private final String applicationId;
    private String largeImage;
    private String largeText;
    private String largeUrl;
    private String smallImage;
    private String smallText;
    private String smallUrl;
    private String inviteCoverImage;

    /**
     * Constructs an ActivityAsset object with the provided assets and application ID.
     *
     * @param assets        The JSON node containing assets information.
     * @param applicationId The ID of the application.
     */
    public ActivityAsset(JsonNode assets, String applicationId) {
        loader = new LazyLoader(assets);
        this.applicationId = applicationId;
    }

    /**
     * Retrieves the large image associated with the activity asset.
     *
     * @return The large image of the activity asset.
     */
    public ActivityImage getLargeImage() {
        largeImage = loader.loadString(largeImage, "large_image");
        return new ActivityImage(applicationId, largeImage);
    }

    /**
     * Retrieves the large text associated with the activity asset.
     *
     * @return The large text of the activity asset.
     */
    public String getLargeText() {
        largeText = loader.loadString(largeText, "large_text");
        return largeText;
    }

    public String getLargeUrl() {
        largeUrl = loader.loadString(largeUrl, "large_url");
        return largeUrl;
    }

    /**
     * Retrieves the small image associated with the activity asset.
     *
     * @return The small image of the activity asset.
     */
    public ActivityImage getSmallImage() {
        smallImage = loader.loadString(smallImage, "small_image");
        return new ActivityImage(applicationId, smallImage);
    }

    /**
     * Retrieves the small text associated with the activity asset.
     *
     * @return The small text of the activity asset.
     */
    public String getSmallText() {
        smallText = loader.loadString(smallText, "small_text");
        return smallText;
    }

    public String getSmallUrl() {
        smallUrl = loader.loadString(smallUrl, "small_url");
        return smallUrl;
    }

    public ActivityImage getInviteCoverImage() {
        inviteCoverImage = loader.loadString(inviteCoverImage, "invite_cover_image");
        return new ActivityImage(applicationId, inviteCoverImage);
    }
}
