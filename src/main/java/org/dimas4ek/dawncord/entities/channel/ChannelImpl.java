package org.dimas4ek.dawncord.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.ApiClient;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.types.ChannelType;
import org.dimas4ek.dawncord.types.GuildMemberFlag;
import org.dimas4ek.dawncord.utils.EnumUtils;

import java.util.List;

public class ChannelImpl implements Channel {
    private final JsonNode channel;
    private final Guild guild;

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
