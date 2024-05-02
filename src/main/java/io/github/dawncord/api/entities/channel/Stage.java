package io.github.dawncord.api.entities.channel;

import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.event.GuildScheduledEvent;
import io.github.dawncord.api.types.StagePrivacyLevel;

/**
 * Represents a stage in a guild.
 */
public interface Stage extends ISnowflake {

    /**
     * Retrieves the guild to which this stage belongs.
     *
     * @return The guild to which this stage belongs.
     */
    Guild getGuild();

    /**
     * Retrieves the channel associated with this stage.
     *
     * @return The channel associated with this stage.
     */
    GuildChannel getChannel();

    /**
     * Retrieves the topic of the stage.
     *
     * @return The topic of the stage.
     */
    String getTopic();

    /**
     * Retrieves the privacy level of the stage.
     *
     * @return The privacy level of the stage.
     */
    StagePrivacyLevel getPrivacyLevel();

    /**
     * Checks if the stage is discoverable.
     *
     * @return True if the stage is discoverable, false otherwise.
     */
    boolean isDiscoverable();

    /**
     * Retrieves the guild scheduled event associated with this stage.
     *
     * @return The guild scheduled event associated with this stage.
     */
    GuildScheduledEvent getGuildEvent();

    /**
     * Deletes the stage.
     */
    void delete();

    /**
     * Modifies the topic of the stage.
     *
     * @param topic The new topic for the stage.
     */
    void modify(String topic);
}
