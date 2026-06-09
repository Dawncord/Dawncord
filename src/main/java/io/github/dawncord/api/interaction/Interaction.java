package io.github.dawncord.api.interaction;

/**
 * Represents an interaction with a bot, typically initiated by a user.
 */
public record Interaction(String interactionId, String interactionToken) {
    /**
     * Constructs an Interaction with the specified interaction ID and token.
     *
     * @param interactionId    The ID of the interaction.
     * @param interactionToken The token associated with the interaction.
     */
    public Interaction {
    }

    /**
     * Retrieves the ID of the interaction.
     *
     * @return The ID of the interaction.
     */
    @Override
    public String interactionId() {
        return interactionId;
    }

    /**
     * Retrieves the token associated with the interaction.
     *
     * @return The token associated with the interaction.
     */
    @Override
    public String interactionToken() {
        return interactionToken;
    }
}
