package org.dimas4ek.wrapper.entities.thread;

import org.dimas4ek.wrapper.utils.MessageUtils;
import org.json.JSONObject;

import java.time.ZonedDateTime;

public class ThreadMetaData {
    private final JSONObject metadata;

    public ThreadMetaData(JSONObject metadata) {
        this.metadata = metadata;
    }

    public boolean isArchived() {
        return metadata.getBoolean("archived");
    }

    public ZonedDateTime getArchiveTimestamp() {
        return MessageUtils.getZonedDateTime(metadata, "archive_timestamp");
    }

    public ZonedDateTime getCreationTimestamp() {
        return MessageUtils.getZonedDateTime(metadata, "create_timestamp");
    }

    public int getArchiveDuration() {
        return metadata.getInt("auto_archive_duration");
    }

    public boolean isLocked() {
        return metadata.getBoolean("locked");
    }
}
