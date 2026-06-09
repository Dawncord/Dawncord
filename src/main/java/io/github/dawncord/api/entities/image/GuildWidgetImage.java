package io.github.dawncord.api.entities.image;

import io.github.dawncord.api.types.ImageFormat;

public class GuildWidgetImage implements Icon {
    private final String url;

    public GuildWidgetImage(String url) {
        this.url = url;
    }

    @Override
    public String getUrl(ImageFormat format) {
        return url;
    }
}
