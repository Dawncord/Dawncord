package org.dimas4ek.dawncord.entities.message;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.entities.ISnowflake;

public class Attachment implements ISnowflake {
    private final JsonNode attachment;
    private String id;
    private String filename;
    private Integer size;
    private String url;
    private String proxyUrl;
    private String contentType;

    public Attachment(JsonNode attachment) {
        this.attachment = attachment;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = attachment.get("id").asText();
        }
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    public String getFilename() {
        if (filename == null) {
            filename = attachment.get("filename").asText();
        }
        return filename;
    }

    public int getSize() {
        if (size == null) {
            size = attachment.get("size").asInt();
        }
        return size;
    }

    public String getUrl() {
        if (url == null) {
            url = attachment.get("url").asText();
        }
        return url;
    }

    public String getProxyUrl() {
        if (proxyUrl == null) {
            proxyUrl = attachment.get("proxy_url").asText();
        }
        return proxyUrl;
    }

    public String getContentType() {
        if (contentType == null) {
            contentType = attachment.get("content_type").asText();
        }
        return contentType;
    }
}
