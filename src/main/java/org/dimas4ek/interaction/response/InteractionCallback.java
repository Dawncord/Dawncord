package org.dimas4ek.interaction.response;

public interface InteractionCallback extends ResponseAction {
    ResponseAction setEphemeral(boolean ephemeral);
}
