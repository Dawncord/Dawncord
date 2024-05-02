package io.github.dawncord.api.entities.image;

import io.github.dawncord.api.Routes;
import io.github.dawncord.api.types.ImageFormat;

/**
 * Represents an activity image associated with an application.
 */
public class ActivityImage implements Icon {
    private final String applicationId;
    private final String imageId;

    /**
     * Constructs an ActivityImage object with the specified application ID and image ID.
     *
     * @param applicationId The ID of the application.
     * @param imageId       The ID of the image.
     */
    public ActivityImage(String applicationId, String imageId) {
        this.applicationId = applicationId;
        this.imageId = imageId;
    }

    @Override
    public String getUrl(ImageFormat format) {
        return Routes.Icon.ActivityImage(applicationId, imageId, format.getExtension());
    }
}
