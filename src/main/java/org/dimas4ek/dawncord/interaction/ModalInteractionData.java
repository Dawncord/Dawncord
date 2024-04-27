package org.dimas4ek.dawncord.interaction;

public class ModalInteractionData implements InteractionData {
    private final Interaction interaction;
    private final String guildId;
    private final String channelId;
    private final String memberId;

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
