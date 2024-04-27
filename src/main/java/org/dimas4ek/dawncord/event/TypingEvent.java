package org.dimas4ek.dawncord.event;

import org.dimas4ek.dawncord.entities.channel.GuildChannel;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.entities.guild.GuildMember;

public class TypingEvent implements Event {
    private final Guild guild;
    private final GuildChannel channel;
    private final GuildMember member;

    public TypingEvent(Guild guild, GuildChannel channel, GuildMember member) {
        this.guild = guild;
        this.channel = channel;
        this.member = member;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    public GuildChannel getChannel() {
        return channel;
    }

    public GuildMember getMember() {
        return member;
    }
}
