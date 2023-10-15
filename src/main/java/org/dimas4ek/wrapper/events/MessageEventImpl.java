package org.dimas4ek.wrapper.events;

import org.dimas4ek.wrapper.entities.Guild;
import org.dimas4ek.wrapper.entities.GuildChannel;
import org.dimas4ek.wrapper.entities.GuildChannelImpl;
import org.dimas4ek.wrapper.entities.Message;
import org.dimas4ek.wrapper.utils.JsonUtils;

public class MessageEventImpl implements MessageEvent {
    private final Message message;
    private final GuildChannel guildChannel;

    public MessageEventImpl(Message message, GuildChannel guildChannel) {
        this.message = message;
        this.guildChannel = guildChannel;
    }

    @Override
    public Message getMessage() {
        return message;
    }

    @Override
    public GuildChannel getChannel() {
        return guildChannel;
    }

    @Override
    public GuildChannel getChannelById(String channelId) {
        return new GuildChannelImpl(JsonUtils.fetchEntity("/channels/" + channelId));
    }

    @Override
    public GuildChannel getChannelById(long channelId) {
        return new GuildChannelImpl(JsonUtils.fetchEntity("/channels/" + channelId));
    }

    @Override
    public Guild getGuild() {
        return null;
    }
}
