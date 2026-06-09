package io.github.dawncord.api.interaction;

/**
 * Represents data associated with a modal interaction.
 */
public record ModalInteractionData(Interaction interaction, String guildId, String channelId,
                                   String memberId) implements InteractionData {
    /**
     * Constructs a ModalInteractionData object with the specified parameters.
     *
     * @param interaction The interaction object associated with this data.
     * @param guildId     The ID of the guild where the interaction occurred.
     * @param channelId   The ID of the channel where the interaction occurred.
     * @param memberId    The ID of the member who initiated the interaction.
     */
    public ModalInteractionData {
    }
}
