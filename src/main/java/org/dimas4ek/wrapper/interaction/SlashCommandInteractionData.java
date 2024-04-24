package org.dimas4ek.wrapper.interaction;

import org.dimas4ek.wrapper.command.SlashCommand;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildMember;

import java.util.List;
import java.util.Map;

public class SlashCommandInteractionData implements InteractionData {
    private final List<Map<String, Object>> options;
    private final SlashCommand slashCommand;
    private final Interaction interaction;
    private final Guild guild;
    private final GuildChannel guildChannel;
    private final GuildMember guildMember;

    public SlashCommandInteractionData(List<Map<String, Object>> options, SlashCommand slashCommand, Interaction interaction, Guild guild, GuildChannel guildChannel, GuildMember guildMember) {
        this.options = options;
        this.slashCommand = slashCommand;
        this.interaction = interaction;
        this.guild = guild;
        this.guildChannel = guildChannel;
        this.guildMember = guildMember;
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

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public GuildMember getGuildMember() {
        return guildMember;
    }

    @Override
    public GuildChannel getGuildChannel() {
        return guildChannel;
    }
}
