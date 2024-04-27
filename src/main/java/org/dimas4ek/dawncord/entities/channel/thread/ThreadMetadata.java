package org.dimas4ek.dawncord.entities.channel.thread;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.utils.MessageUtils;

import java.time.ZonedDateTime;

public class ThreadMetadata {
    private final JsonNode metadata;
    private Boolean isArchived;
    private ZonedDateTime archiveTimestamp;
    private ZonedDateTime creationTimestamp;
    private Integer archiveDuration;
    private Boolean isLocked;

    public ThreadMetadata(JsonNode metadata) {
        this.metadata = metadata;
    }

    public boolean isArchived() {
        if (isArchived == null) {
            isArchived = metadata.get("archived").asBoolean();
        }
        return isArchived;
    }

    public ZonedDateTime getArchiveTimestamp() {
        if (archiveTimestamp == null) {
            archiveTimestamp = MessageUtils.getZonedDateTime(metadata, "archive_timestamp");
        }
        return archiveTimestamp;
    }

    public ZonedDateTime getCreationTimestamp() {
        if (creationTimestamp == null) {
            creationTimestamp = MessageUtils.getZonedDateTime(metadata, "create_timestamp");
        }
        return creationTimestamp;
    }

    public int getArchiveDuration() {
        if (archiveDuration == null) {
            archiveDuration = metadata.get("auto_archive_duration").asInt();
        }
        return archiveDuration;
    }

    public boolean isLocked() {
        if (isLocked == null) {
            isLocked = metadata.get("locked").asBoolean();
        }
        return isLocked;
    }
}
