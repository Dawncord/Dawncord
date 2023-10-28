package org.dimas4ek.wrapper.entities.image;

import org.dimas4ek.wrapper.types.ImageFormat;

public interface Icon {
    String getUrl(ImageFormat format);

    void download();
}
