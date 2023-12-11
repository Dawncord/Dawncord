package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.message.Message;

public class MessageEventImpl implements MessageEvent {
    private final Message message;
    private final GuildChannel guildChannel;
    private final Guild guild;

    public MessageEventImpl(Message message, GuildChannel guildChannel, Guild guild) {
        this.message = message;
        this.guildChannel = guildChannel;
        this.guild = guild;
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
        return guild.getChannelById(channelId);
    }

    @Override
    public GuildChannel getChannelById(long channelId) {
        return getChannelById(String.valueOf(channelId));
    }

    @Override
    public Guild getGuild() {
        return guild;
    }
}
