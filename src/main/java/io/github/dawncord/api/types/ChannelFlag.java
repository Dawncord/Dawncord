package io.github.dawncord.api.types;

/**
 * Represents flags associated with channels.
 */
public enum ChannelFlag {
    /**
     * This thread is pinned to the top of its parent GUILD_FORUM or GUILD_MEDIA channel.
     */
    PINNED(1 << 1),

    /**
     * Whether a tag is required to be specified when creating a thread in a GUILD_FORUM or a GUILD_MEDIA channel.
     * Tags are specified in the applied_tags field.
     */
    REQUIRE_TAG(1 << 4),

    /**
     * When set, hides the embedded media download options. Available only for media channels.
     */
    HIDE_MEDIA_DOWNLOAD_OPTIONS(1 << 15);

    private final int value;

    ChannelFlag(int value) {
        this.value = value;
    }

    /**
     * Gets the value associated with the channel flag.
     *
     * @return The value associated with the channel flag.
     */
    public int getValue() {
        return value;
    }
}
