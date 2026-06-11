package io.github.dawncord.api.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.guild.Guild;

/**
 * Represents a category channel within a guild.
 */
public class GuildCategory extends Channel {
    /**
     * Constructs a GuildCategory object with the given JSON node representing the category channel
     * and the guild to which it belongs.
     *
     * @param category The JSON node representing the category channel.
     * @param guild    The guild to which the category channel belongs.
     */
    public GuildCategory(JsonNode category, Guild guild) {
        super(category, guild);
    }
}
