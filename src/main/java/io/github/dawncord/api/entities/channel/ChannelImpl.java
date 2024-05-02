package io.github.dawncord.api.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.types.ChannelType;
import io.github.dawncord.api.types.GuildMemberFlag;
import io.github.dawncord.api.utils.EnumUtils;

import java.util.List;

/**
 * Represents an implementation of the Channel interface.
 */
public class ChannelImpl implements Channel {

    private final JsonNode channel;
    private final Guild guild;

    /**
     * Constructs a ChannelImpl object.
     *
     * @param channel The JSON representation of the channel.
     * @param guild   The guild to which this channel belongs.
     */
    public ChannelImpl(JsonNode channel, Guild guild) {
        this.channel = channel;
        this.guild = guild;
    }

    @Override
    public String getId() {
        return channel.get("id").asText();
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        return channel.get("name").asText();
    }

    @Override
    public ChannelType getType() {
        return EnumUtils.getEnumObject(channel, "type", ChannelType.class);
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public List<GuildMemberFlag> getFlags() {
        return EnumUtils.getEnumListFromLong(channel, "flags", GuildMemberFlag.class);
    }

    @Override
    public void delete() {
        ApiClient.delete(Routes.Channel.Get(getId()));
    }

    @Override
    public String getAsMention() {
        return "<#" + getId() + ">";
    }
}
