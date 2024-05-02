package io.github.dawncord.api.interaction;

import io.github.dawncord.api.command.SlashCommand;

import java.util.List;
import java.util.Map;

/**
 * Represents data associated with a slash command interaction.
 */
public class SlashCommandInteractionData implements InteractionData {
    private final List<Map<String, Object>> options;
    private final SlashCommand slashCommand;
    private final Interaction interaction;
    private final String guildId;
    private final String channelId;
    private final String memberId;

    /**
     * Constructs a SlashCommandInteractionData object with the specified parameters.
     *
     * @param options      The list of options associated with the command.
     * @param slashCommand The slash command associated with this interaction.
     * @param interaction  The interaction object associated with this data.
     * @param guildId      The ID of the guild where the interaction occurred.
     * @param channelId    The ID of the channel where the interaction occurred.
     * @param memberId     The ID of the member who initiated the interaction.
     */
    public SlashCommandInteractionData(List<Map<String, Object>> options, SlashCommand slashCommand, Interaction interaction, String guildId, String channelId, String memberId) {
        this.options = options;
        this.slashCommand = slashCommand;
        this.interaction = interaction;
        this.guildId = guildId;
        this.channelId = channelId;
        this.memberId = memberId;
    }

    /**
     * Retrieves the list of options associated with the command.
     *
     * @return The list of options associated with the command.
     */
    public List<Map<String, Object>> getOptions() {
        return options;
    }

    /**
     * Retrieves the slash command associated with this interaction.
     *
     * @return The slash command associated with this interaction.
     */
    public SlashCommand getSlashCommand() {
        return slashCommand;
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

    @Override
    public Interaction getInteraction() {
        return interaction;
    }
}
