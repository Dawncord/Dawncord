package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.message.Message;

public class MessageEvent implements Event {
    private final Message message;
    private final GuildChannel guildChannel;
    private final Guild guild;

    public MessageEvent(Message message, GuildChannel guildChannel, Guild guild) {
        this.message = message;
        this.guildChannel = guildChannel;
        this.guild = guild;
    }

    public Message getMessage() {
        return message;
    }

    public GuildChannel getChannel() {
        return guildChannel;
    }

    public GuildChannel getChannelById(String channelId) {
        return guild.getChannelById(channelId);
    }

    public GuildChannel getChannelById(long channelId) {
        return getChannelById(String.valueOf(channelId));
    }

    @Override
    public Guild getGuild() {
        return guild;
    }
}
