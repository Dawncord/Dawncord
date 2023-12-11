package org.dimas4ek.wrapper.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.types.ChannelType;
import org.dimas4ek.wrapper.types.GuildMemberFlag;
import org.dimas4ek.wrapper.utils.EnumUtils;

import java.util.List;

public class ChannelImpl implements Channel {
    private final JsonNode channel;
    private final Guild guild;
    private String id;
    private String name;
    private ChannelType type;
    private List<GuildMemberFlag> flags;

    public ChannelImpl(JsonNode channel, Guild guild) {
        this.channel = channel;
        this.guild = guild;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = channel.get("id").asText();
        }
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        if (name == null) {
            name = channel.get("name").asText();
        }
        return name;
    }

    @Override
    public ChannelType getType() {
        if (type == null) {
            type = EnumUtils.getEnumObject(channel, "type", ChannelType.class);
        }
        return type;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public List<GuildMemberFlag> getFlags() {
        if (flags == null) {
            flags = EnumUtils.getEnumListFromLong(channel, "flags", GuildMemberFlag.class);
        }
        return flags;
    }

    @Override
    public void delete() {
        ApiClient.delete("/channels/" + getId());
    }

    @Override
    public String getAsMention() {
        return "<#" + getId() + ">";
    }
}
