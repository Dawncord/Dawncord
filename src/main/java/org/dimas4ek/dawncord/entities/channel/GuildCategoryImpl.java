package org.dimas4ek.dawncord.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.entities.guild.Guild;

public class GuildCategoryImpl extends ChannelImpl implements GuildCategory {
    public GuildCategoryImpl(JsonNode category, Guild guild) {
        super(category, guild);
    }
}
