package org.dimas4ek.wrapper.events;

import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.slashcommand.option.OptionData;

import java.util.List;

public interface SlashCommandEvent {
    String getCommandName();
    void reply(String message);
    Guild getGuild();
    GuildChannel getChannel();
    GuildChannel getChannelById(String channelId);
    GuildChannel getChannelById(long channelId);
    List<OptionData> getOptions();
    OptionData getOption(String name);
}
