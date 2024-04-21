package org.dimas4ek.wrapper.entities.image;

import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.types.ImageFormat;

public class ActivityImage implements Icon {
    private final String applicationId;
    private final String imageId;

    public ActivityImage(String applicationId, String imageId) {
        this.applicationId = applicationId;
        this.imageId = imageId;
    }

    @Override
    public String getUrl(ImageFormat format) {
        return Routes.Icon.ActivityImage(applicationId, imageId, format.getExtension());
    }
}
