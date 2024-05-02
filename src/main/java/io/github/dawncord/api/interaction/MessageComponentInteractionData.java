package io.github.dawncord.api.interaction;

/**
 * Represents data associated with a message component interaction.
 */
public class MessageComponentInteractionData implements InteractionData {
    private final Interaction interaction;
    private final String customId;
    private final String id;
    private final String guildId;
    private final String channelId;
    private final String memberId;

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
    public MessageComponentInteractionData(Interaction interaction, String customId, String id, String guildId, String channelId, String memberId) {
        this.interaction = interaction;
        this.customId = customId;
        this.id = id;
        this.guildId = guildId;
        this.channelId = channelId;
        this.memberId = memberId;
    }

    /**
     * Retrieves the custom ID of the message component.
     *
     * @return The custom ID of the message component.
     */
    public String getCustomId() {
        return customId;
    }

    /**
     * Retrieves the ID of the message component.
     *
     * @return The ID of the message component.
     */
    public String getId() {
        return id;
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
