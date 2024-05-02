package io.github.dawncord.api.interaction;

/**
 * Represents data associated with a modal interaction.
 */
public class ModalInteractionData implements InteractionData {
    private final Interaction interaction;
    private final String guildId;
    private final String channelId;
    private final String memberId;

    /**
     * Constructs a ModalInteractionData object with the specified parameters.
     *
     * @param interaction The interaction object associated with this data.
     * @param guildId     The ID of the guild where the interaction occurred.
     * @param channelId   The ID of the channel where the interaction occurred.
     * @param memberId    The ID of the member who initiated the interaction.
     */
    public ModalInteractionData(Interaction interaction, String guildId, String channelId, String memberId) {
        this.interaction = interaction;
        this.guildId = guildId;
        this.channelId = channelId;
        this.memberId = memberId;
    }

    @Override
    public Interaction getInteraction() {
        return interaction;
    }

    @Override
    public String getGuildId() {
        return guildId;
    }

    @Override
    public String getChannelId() {
        return channelId;
    }

    @Override
    public String getMemberId() {
        return memberId;
    }
}
