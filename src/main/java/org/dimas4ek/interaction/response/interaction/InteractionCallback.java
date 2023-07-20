package org.dimas4ek.interaction.response.interaction;

public interface InteractionCallback extends InteractionResponse  {
    InteractionResponse setEphemeral(boolean ephemeral);
}
