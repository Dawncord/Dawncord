package org.dimas4ek.event.slashcommand.interaction;

import org.dimas4ek.enities.embed.Embed;
import org.dimas4ek.enities.guild.Guild;
import org.dimas4ek.enities.guild.GuildChannel;
import org.dimas4ek.interaction.response.interaction.InteractionCallback;

public interface SlashCommandInteractionEvent {
    String getCommandName();
    
    InteractionCallback reply(String message);
    void replyWithEmbed(Embed embed);
    void deferReply();
    Guild getGuild();
    GuildChannel getChannel();
}

