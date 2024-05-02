package io.github.dawncord.api.types;

/**
 * Represents the type of channel.
 */
public enum ChannelType {
    /**
     * Text channel within a guild.
     */
    GUILD_TEXT(0),

    /**
     * Direct message channel.
     */
    DM(1),

    /**
     * Voice channel within a guild.
     */
    GUILD_VOICE(2),

    /**
     * Group direct message channel.
     */
    GROUP_DM(3),

    /**
     * Category channel within a guild.
     */
    GUILD_CATEGORY(4),

    /**
     * Announcement channel within a guild.
     */
    GUILD_ANNOUNCEMENT(5),

    /**
     * Announcement thread within a guild.
     */
    ANNOUNCEMENT_THREAD(10),

    /**
     * Public thread within a guild.
     */
    PUBLIC_THREAD(11),

    /**
     * Private thread within a guild.
     */
    PRIVATE_THREAD(12),

    /**
     * Stage voice channel within a guild.
     */
    GUILD_STAGE_VOICE(13),

    /**
     * Directory channel within a guild.
     */
    GUILD_DIRECTORY(14),

    /**
     * Forum channel within a guild.
     */
    GUILD_FORUM(15),

    /**
     * Media channel within a guild.
     */
    GUILD_MEDIA(16);

    private final int value;

    ChannelType(int value) {
        this.value = value;
    }

    /**
     * Gets the value associated with the channel type.
     *
     * @return The value associated with the channel type.
     */
    public int getValue() {
        return value;
    }
}
