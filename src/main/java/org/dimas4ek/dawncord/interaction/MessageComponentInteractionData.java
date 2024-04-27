package org.dimas4ek.dawncord.interaction;

public class MessageComponentInteractionData implements InteractionData {
    private final Interaction interaction;
    private final String customId;
    private final String id;
    private final String guildId;
    private final String channelId;
    private final String memberId;

    public MessageComponentInteractionData(Interaction interaction, String customId, String id, String guildId, String channelId, String memberId) {
        this.interaction = interaction;
        this.customId = customId;
        this.id = id;
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

    public String getCustomId() {
        return customId;
    }

    public String getId() {
        return id;
    }
}
