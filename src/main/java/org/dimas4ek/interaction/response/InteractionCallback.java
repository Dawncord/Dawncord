package org.dimas4ek.interaction.response;

import org.dimas4ek.enities.component.ActionRow;
import org.dimas4ek.enities.component.MessageComponent;
import org.dimas4ek.enities.embed.Embed;

public interface InteractionCallback extends ResponseAction {
    ResponseAction setEphemeral(boolean ephemeral);
    ResponseAction addEmbeds(Embed embed);
    
    ResponseAction addComponents(MessageComponent component);
    ResponseAction addComponents(ActionRow... actionRow);
}
