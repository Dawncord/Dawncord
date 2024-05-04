package io.github.dawncord.api.entities.channel.thread;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.utils.TimeUtils;

import java.time.ZonedDateTime;

/**
 * Represents metadata associated with a thread.
 */
public class ThreadMetadata {
    private final JsonNode metadata;
    private Boolean isArchived;
    private ZonedDateTime archiveTimestamp;
    private ZonedDateTime creationTimestamp;
    private Integer archiveDuration;
    private Boolean isLocked;

    /**
     * Constructs a new ThreadMetadata instance with the provided metadata.
     *
     * @param metadata The metadata associated with the thread.
     */
    public ThreadMetadata(JsonNode metadata) {
        this.metadata = metadata;
    }

    /**
     * Checks if the thread is archived.
     *
     * @return True if the thread is archived, false otherwise.
     */
    public boolean isArchived() {
        if (isArchived == null) {
            isArchived = metadata.get("archived").asBoolean();
        }
        return isArchived;
    }

    /**
     * Retrieves the timestamp when the thread was archived.
     *
     * @return The archive timestamp.
     */
    public ZonedDateTime getArchiveTimestamp() {
        if (archiveTimestamp == null) {
            archiveTimestamp = TimeUtils.getZonedDateTime(metadata, "archive_timestamp");
        }
        return archiveTimestamp;
    }

    /**
     * Retrieves the timestamp when the thread was created.
     *
     * @return The creation timestamp.
     */
    public ZonedDateTime getCreationTimestamp() {
        if (creationTimestamp == null) {
            creationTimestamp = TimeUtils.getZonedDateTime(metadata, "create_timestamp");
        }
        return creationTimestamp;
    }

    /**
     * Retrieves the duration for which the thread will be automatically archived.
     *
     * @return The auto-archive duration.
     */
    public int getArchiveDuration() {
        if (archiveDuration == null) {
            archiveDuration = metadata.get("auto_archive_duration").asInt();
        }
        return archiveDuration;
    }

    /**
     * Checks if the thread is locked.
     *
     * @return True if the thread is locked, false otherwise.
     */
    public boolean isLocked() {
        if (isLocked == null) {
            isLocked = metadata.get("locked").asBoolean();
        }
        return isLocked;
    }
}
