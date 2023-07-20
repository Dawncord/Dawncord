package org.dimas4ek.event;

import org.dimas4ek.enities.embed.Embed;
import org.dimas4ek.enities.guild.Guild;
import org.dimas4ek.enities.guild.GuildChannel;
import org.dimas4ek.event.interaction.response.InteractionCallback;

public interface SlashCommandInteractionEvent {
    InteractionCallback reply(String message);
    void replyWithEmbed(Embed embed);
    void deferReply();
    Guild getGuild();
    GuildChannel getChannel();
}

