package io.github.dawncord.api.entities.image;

import io.github.dawncord.api.types.ImageFormat;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

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
