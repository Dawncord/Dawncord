package io.github.dawncord.api.interaction;

/**
 * Represents data associated with a message component interaction.
 */
public record MessageComponentInteractionData(Interaction interaction, String customId, String id, String guildId,
                                              String channelId, String memberId) implements InteractionData {
    /**
     * Constructs a MessageComponentInteractionData object with the specified parameters.
     *
     * @param interaction The interaction object associated with this data.
     * @param customId    The custom ID of the message component.
     * @param id          The ID of the message component.
     * @param guildId     The ID of the guild where the interaction occurred.
     * @param channelId   The ID of the channel where the interaction occurred.
     * @param memberId    The ID of the member who initiated the interaction.
     */
    public MessageComponentInteractionData {
    }

    /**
     * Retrieves the custom ID of the message component.
     *
     * @return The custom ID of the message component.
     */
    @Override
    public String customId() {
        return customId;
    }

    /**
     * Retrieves the ID of the message component.
     *
     * @return The ID of the message component.
     */
    @Override
    public String id() {
        return id;
    }
}
