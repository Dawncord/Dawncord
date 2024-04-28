package org.dimas4ek.dawncord.interaction;

import org.dimas4ek.dawncord.command.SlashCommand;

import java.util.List;
import java.util.Map;

public class SlashCommandInteractionData implements InteractionData {
    private final List<Map<String, Object>> options;
    private final SlashCommand slashCommand;
    private final Interaction interaction;
    private final String guildId;
    private final String channelId;
    private final String memberId;

    public SlashCommandInteractionData(List<Map<String, Object>> options, SlashCommand slashCommand, Interaction interaction, String guildId, String channelId, String memberId) {
        this.options = options;
        this.slashCommand = slashCommand;
        this.interaction = interaction;
        this.guildId = guildId;
        this.channelId = channelId;
        this.memberId = memberId;
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

    public List<Map<String, Object>> getOptions() {
        return options;
    }

    public SlashCommand getSlashCommand() {
        return slashCommand;
    }

    @Override
    public Interaction getInteraction() {
        return interaction;
    }
}
