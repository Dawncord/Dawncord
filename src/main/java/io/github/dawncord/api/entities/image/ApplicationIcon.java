package io.github.dawncord.api.entities.image;

import io.github.dawncord.api.Routes;
import io.github.dawncord.api.types.ImageFormat;

/**
 * Represents an icon associated with an application.
 */
public class ApplicationIcon implements Icon {
    private final String applicationId;
    private final String hash;

    /**
     * Constructs an ApplicationIcon object with the specified application ID and hash.
     *
     * @param applicationId The ID of the application.
     * @param hash          The hash value of the icon.
     */
    public ApplicationIcon(String applicationId, String hash) {
        this.applicationId = applicationId;
        this.hash = hash;
    }

    @Override
    public String getUrl(ImageFormat format) {
        return Routes.Icon.ApplicationIcon(applicationId, hash, format.getExtension());
    }
}
