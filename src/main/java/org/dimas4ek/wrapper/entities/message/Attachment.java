package org.dimas4ek.wrapper.entities.message;

import org.json.JSONObject;

public class Attachment {
    private final JSONObject attachment;

    public Attachment(JSONObject attachment) {
        this.attachment = attachment;
    }

    public String getId() {
        return attachment.getString("id");
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
