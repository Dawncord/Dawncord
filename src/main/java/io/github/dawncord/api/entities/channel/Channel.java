package io.github.dawncord.api.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.IMentionable;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.types.ChannelFlag;
import io.github.dawncord.api.types.ChannelType;
import io.github.dawncord.api.utils.LazyLoader;

import java.util.List;

/**
 * Represents a Discord channel.
 */
public class Channel implements ISnowflake, IMentionable {
    private final LazyLoader loader;
    private final Guild guild;
    private String id;
    private String name;
    private ChannelType type;
    private List<ChannelFlag> flags;

    /**
     * Constructs a Channel object.
     *
     * @param channel The JSON representation of the channel.
     * @param guild   The guild to which this channel belongs.
     */
    public Channel(JsonNode channel, Guild guild) {
        this.guild = guild;
        loader = new LazyLoader(channel);
    }

    @Override
    public String getId() {
        id = loader.loadString(id, "id");
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    public String getName() {
        name = loader.loadString(name, "name");
        return name;
    }

    public ChannelType getType() {
        type = loader.loadEnumObject(type, "type", ChannelType.class);
        return type;
    }

    public Guild getGuild() {
        return guild;
    }

    public List<ChannelFlag> getFlags() {
        flags = loader.loadEnumListFromLong(flags, "flags", ChannelFlag.class);
        return flags;
    }

    public void delete() {
        ApiClient.delete(Routes.Channel.Get(getId()));
    }

    @Override
    public String getAsMention() {
        return "<#" + getId() + ">";
    }
}
