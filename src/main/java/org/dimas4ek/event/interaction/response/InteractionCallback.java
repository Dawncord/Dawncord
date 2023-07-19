package org.dimas4ek.event.interaction.response;

public interface InteractionCallback extends InteractionResponse  {
    InteractionResponse setEphemeral(boolean ephemeral);
}
