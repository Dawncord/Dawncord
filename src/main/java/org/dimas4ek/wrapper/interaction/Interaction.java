package org.dimas4ek.wrapper.interaction;

public class Interaction {
    private final String interactionId;
    private final String interactionToken;

    public Interaction(String interactionId, String interactionToken) {
        this.interactionId = interactionId;
        this.interactionToken = interactionToken;
    }

    public String getInteractionId() {
        return interactionId;
    }

    public String getInteractionToken() {
        return interactionToken;
    }
}
