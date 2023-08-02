package org.dimas4ek.enities.guild.action;

import org.dimas4ek.enities.embed.Embed;
import org.dimas4ek.interaction.response.ResponseAction;

public interface MessageCreateAction extends ResponseAction {
    MessageCreateAction withEmbeds(Embed embed);
}
