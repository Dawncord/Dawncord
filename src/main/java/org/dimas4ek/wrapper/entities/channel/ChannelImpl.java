package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.types.ChannelType;
import org.dimas4ek.wrapper.types.GuildMemberFlag;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONObject;

import java.util.List;

public class ChannelImpl implements Channel {
    private final JSONObject channel;

    public ChannelImpl(JSONObject channel) {
        this.channel = channel;
    }

    @Override
    public String getId() {
        return channel.getString("id");
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        return channel.getString("name");
    }

    @Override
    public ChannelType getType() {
        return EnumUtils.getEnumObject(channel, "type", ChannelType.class);
        /*for (ChannelType type : ChannelType.values()) {
            if (channel.getInt("type") == type.getValue()) {
                return type;
            }
        }
        return null;*/
    }

    @Override
    public Guild getGuild() {
        return new GuildImpl(JsonUtils.fetchEntity("/guilds/" + channel.getString("guild_id")));
    }

    @Override
    public List<GuildMemberFlag> getFlags() {
        return EnumUtils.getEnumListFromLong(channel, "flags", GuildMemberFlag.class);
        /*if (channel.has("flags") || channel.getInt("flags") != 0) {
            List<String> flags = new ArrayList<>();
            long flagsFromJson = Long.parseLong(String.valueOf(channel.getInt("flags")));
            for (GuildMemberFlag flag : GuildMemberFlag.values()) {
                if ((flagsFromJson & flag.getValue()) != 0) {
                    flags.add(flag.name());
                }
            }
            return flags;
        }
        return Collections.emptyList();*/
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
