package org.dimas4ek.dawncord.types;

public enum ChannelFlag {
    PINNED(1 << 1, "this thread is pinned to the top of its parent GUILD_FORUM or GUILD_MEDIA channel"),
    REQUIRE_TAG(1 << 4, "whether a tag is required to be specified when creating a thread in a GUILD_FORUM or a GUILD_MEDIA channel. Tags are specified in the applied_tags field"),
    HIDE_MEDIA_DOWNLOAD_OPTIONS(1 << 15, "when set hides the embedded media download options. Available only for media channels");

    private final int value;
    private final String description;

    ChannelFlag(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
