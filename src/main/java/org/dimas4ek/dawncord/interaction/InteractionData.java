package org.dimas4ek.dawncord.interaction;

public interface InteractionData {
    Interaction getInteraction();

    String getGuildId();

    String getChannelId();

    String getMemberId();
}
