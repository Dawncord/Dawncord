package io.github.dawncord.api.entities.message;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.ISnowflake;

/**
 * Represents an attachment associated with a message.
 */
public class Attachment implements ISnowflake {
    private final JsonNode attachment;
    private String id;
    private String filename;
    private Integer size;
    private String url;
    private String proxyUrl;
    private String contentType;

    /**
     * Initializes an Attachment object with the provided JSON node.
     *
     * @param attachment The JSON node representing the attachment.
     */
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

    /**
     * Retrieves the filename of the attachment.
     *
     * @return The filename of the attachment.
     */
    public String getFilename() {
        if (filename == null) {
            filename = attachment.get("filename").asText();
        }
        return filename;
    }

    /**
     * Retrieves the size of the attachment.
     *
     * @return The size of the attachment.
     */
    public int getSize() {
        if (size == null) {
            size = attachment.get("size").asInt();
        }
        return size;
    }

    /**
     * Retrieves the URL of the attachment.
     *
     * @return The URL of the attachment.
     */
    public String getUrl() {
        if (url == null) {
            url = attachment.get("url").asText();
        }
        return url;
    }

    /**
     * Retrieves the proxy URL of the attachment.
     *
     * @return The proxy URL of the attachment.
     */
    public String getProxyUrl() {
        if (proxyUrl == null) {
            proxyUrl = attachment.get("proxy_url").asText();
        }
        return proxyUrl;
    }

    /**
     * Retrieves the content type of the attachment.
     *
     * @return The content type of the attachment.
     */
    public String getContentType() {
        if (contentType == null) {
            contentType = attachment.get("content_type").asText();
        }
        return contentType;
    }
}
