package org.dimas4ek.event.slashcommand.interaction;

import org.dimas4ek.enities.embed.Embed;
import org.dimas4ek.enities.guild.Guild;
import org.dimas4ek.enities.guild.GuildChannel;
import org.dimas4ek.enities.guild.OptionItem;
import org.dimas4ek.interaction.response.InteractionCallback;

import java.util.List;

public interface SlashCommandInteractionEvent {
    String getCommandName();
    InteractionCallback reply(String message);
    InteractionCallback deferReply();
    void replyWithEmbed(Embed embed);
    Guild getGuild();
    GuildChannel getChannel();
    List<OptionItem> getOptions();
}

