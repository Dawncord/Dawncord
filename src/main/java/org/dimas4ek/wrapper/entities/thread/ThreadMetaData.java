package org.dimas4ek.wrapper.entities.thread;

import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ThreadMetaData {
    private final JSONObject metadata;

    public ThreadMetaData(JSONObject metadata) {
        this.metadata = metadata;
    }

    public boolean isArchived() {
        return metadata.getBoolean("archived");
    }

    public ZonedDateTime getArchiveTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return ZonedDateTime.parse(metadata.getString("archive_timestamp"), formatter);
    }

    public ZonedDateTime getCreationTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return ZonedDateTime.parse(metadata.getString("create_timestamp"), formatter);
    }

    public int getArchiveDuration() {
        return metadata.getInt("auto_archive_duration");
    }

    public boolean isLocked() {
        return metadata.getBoolean("locked");
    }
}
