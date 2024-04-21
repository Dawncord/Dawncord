package org.dimas4ek.wrapper.entities.image;

import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.types.ImageFormat;

public class ApplicationIcon implements Icon {
    private final String applicationId;
    private final String hash;

    public ApplicationIcon(String applicationId, String hash) {
        this.applicationId = applicationId;
        this.hash = hash;
    }

    @Override
    public String getUrl(ImageFormat format) {
        return Routes.Icon.ApplicationIcon(applicationId, hash, format.getExtension());
    }
}
