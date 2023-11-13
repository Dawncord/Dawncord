package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.application.Application;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildMember;
import org.dimas4ek.wrapper.slashcommand.option.OptionData;

import java.util.List;

public interface SlashCommandEvent {
    String getCommandName();

    void reply(String message);

    GuildMember getMember();

    User getUser();

    Guild getGuild();

    GuildChannel getChannel();

    GuildChannel getChannelById(String channelId);

    GuildChannel getChannelById(long channelId);

    List<OptionData> getOptions();

    OptionData getOption(String name);

    Application getApplication();

    void editApplication();
}
