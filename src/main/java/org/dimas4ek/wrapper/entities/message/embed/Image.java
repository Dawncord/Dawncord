package org.dimas4ek.wrapper.entities.message.embed;

import org.json.JSONObject;

public class Image {
    private final JSONObject image;

    public Image(JSONObject image) {
        this.image = image;
    }

    public String getUrl() {
        return image.getString("url");
    }

    public String getProxyUrl() {
        return image.getString("proxy_url");
    }

    public int getWidth() {
        return image.getInt("width");
    }

    public int getHeight() {
        return image.getInt("height");
    }
}
