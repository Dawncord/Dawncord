package io.github.dawncord.api.interaction;

/**
 * Represents an interaction with a bot, typically initiated by a user.
 */
public class Interaction {
    private final String interactionId;
    private final String interactionToken;

    /**
     * Constructs an Interaction with the specified interaction ID and token.
     *
     * @param interactionId    The ID of the interaction.
     * @param interactionToken The token associated with the interaction.
     */
    public Interaction(String interactionId, String interactionToken) {
        this.interactionId = interactionId;
        this.interactionToken = interactionToken;
    }

    /**
     * Retrieves the ID of the interaction.
     *
     * @return The ID of the interaction.
     */
    public String getInteractionId() {
        return interactionId;
    }

    /**
     * Retrieves the token associated with the interaction.
     *
     * @return The token associated with the interaction.
     */
    public String getInteractionToken() {
        return interactionToken;
    }
}
