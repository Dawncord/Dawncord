package io.github.dawncord.api.entities.channel.thread;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.utils.LazyLoader;

import java.time.ZonedDateTime;

/**
 * Represents metadata associated with a thread.
 */
public class ThreadMetadata {
    private final LazyLoader loader;
    private Boolean archived;
    private ZonedDateTime archiveTimestamp;
    private Integer archiveDuration;
    private Boolean locked;

    /**
     * Constructs a new ThreadMetadata instance with the provided metadata.
     *
     * @param metadata The metadata associated with the thread.
     */
    public ThreadMetadata(JsonNode metadata) {
        loader = new LazyLoader(metadata);
    }

    /**
     * Checks if the thread is archived.
     *
     * @return True if the thread is archived, false otherwise.
     */
    public boolean isArchived() {
        archived = loader.loadBoolean(archived, "archived");
        return archived;
    }

    /**
     * Retrieves the timestamp when the thread was archived.
     *
     * @return The archive timestamp.
     */
    public ZonedDateTime getArchiveTimestamp() {
        archiveTimestamp = loader.loadZonedDateTime(archiveTimestamp, "archive_timestamp");
        return archiveTimestamp;
    }

    /**
     * Retrieves the duration for which the thread will be automatically archived.
     *
     * @return The auto-archive duration.
     */
    public int getArchiveDuration() {
        archiveDuration = loader.loadInt(archiveDuration, "auto_archive_duration");
        return archiveDuration;
    }

    /**
     * Checks if the thread is locked.
     *
     * @return True if the thread is locked, false otherwise.
     */
    public boolean isLocked() {
        locked = loader.loadBoolean(locked, "locked");
        return locked;
    }
}
