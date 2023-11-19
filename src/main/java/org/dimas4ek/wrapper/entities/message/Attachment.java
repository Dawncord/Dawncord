package org.dimas4ek.wrapper.entities.message;

import org.dimas4ek.wrapper.entities.ISnowflake;
import org.json.JSONObject;

public class Attachment implements ISnowflake {
    private final JSONObject attachment;

    public Attachment(JSONObject attachment) {
        this.attachment = attachment;
    }

    @Override
    public String getId() {
        return attachment.getString("id");
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    public String getFilename() {
        return attachment.getString("filename");
    }

    public int getSize() {
        return attachment.getInt("size");
    }

    public String getUrl() {
        return attachment.getString("url");
    }

    public String getProxyUrl() {
        return attachment.getString("proxy_url");
    }

    public String getContentType() {
        return attachment.getString("content_type");
    }
}
