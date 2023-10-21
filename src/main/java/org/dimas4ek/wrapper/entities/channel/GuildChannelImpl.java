package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.action.ChannelModifyAction;
import org.dimas4ek.wrapper.entities.IMentionable;
import org.dimas4ek.wrapper.types.ChannelType;
import org.json.JSONObject;

public class GuildChannelImpl implements GuildChannel, IMentionable {
    private final JSONObject channel;

    public GuildChannelImpl(JSONObject channel) {
        this.channel = channel;
    }

    @Override
    public TextChannel asText() {
        return new TextChannelImpl(channel);
    }

    @Override
    public VoiceChannel asVoice() {
        return new VoiceChannelImpl(channel);
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
    public String getType() {
        for (ChannelType type : ChannelType.values()) {
            if (channel.getInt("type") == type.getValue()) {
                return type.toString();
            }
        }
        return null;
    }

    @Override
    public ChannelType getTypeRaw() {
        for (ChannelType type : ChannelType.values()) {
            if (channel.getInt("type") == type.getValue()) {
                return type;
            }
        }
        return null;
    }

    @Override
    public ChannelModifyAction modify() {
        return new ChannelModifyAction(this);
    }

    @Override
    public GuildCategory getCategory() {
        if (channel.get("parent_id") != null) {
            JSONObject category = ApiClient.getJsonObject("/channels/" + channel.getString("parent_id"));
            return new GuildCategoryImpl(category);
        }
        return null;
    }

    @Override
    public String getAsMention() {
        return "<#" + getId() + ">";
    }
}
