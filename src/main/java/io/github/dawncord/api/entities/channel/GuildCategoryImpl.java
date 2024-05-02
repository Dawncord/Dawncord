package io.github.dawncord.api.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.guild.Guild;

/**
 * Implementation of the GuildCategory interface representing a category channel within a guild.
 * Extends the ChannelImpl class.
 */
public class GuildCategoryImpl extends ChannelImpl implements GuildCategory {
    /**
     * Constructs a GuildCategoryImpl object with the given JSON node representing the category channel
     * and the guild to which it belongs.
     *
     * @param category The JSON node representing the category channel.
     * @param guild    The guild to which the category channel belongs.
     */
    public GuildCategoryImpl(JsonNode category, Guild guild) {
        super(category, guild);
    }
}
