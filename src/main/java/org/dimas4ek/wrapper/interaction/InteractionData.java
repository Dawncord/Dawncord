package org.dimas4ek.wrapper.interaction;

import org.dimas4ek.wrapper.command.SlashCommand;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildMember;

import java.util.List;
import java.util.Map;

public class InteractionData {
    private final List<Map<String, Object>> options;
    private final SlashCommand slashCommand;
    private final Interaction interaction;
    private final Guild guild;
    private final GuildMember guildMember;
    private final GuildChannel guildChannel;

    public InteractionData(List<Map<String, Object>> options, SlashCommand slashCommand, Interaction interaction, Guild guild, GuildMember guildMember, GuildChannel guildChannel) {
        this.options = options;
        this.slashCommand = slashCommand;
        this.interaction = interaction;
        this.guild = guild;
        this.guildMember = guildMember;
        this.guildChannel = guildChannel;
    }

    public List<Map<String, Object>> getOptions() {
        return options;
    }

    public SlashCommand getSlashCommand() {
        return slashCommand;
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public Guild getGuild() {
        return guild;
    }

    public GuildMember getGuildMember() {
        return guildMember;
    }

    public GuildChannel getGuildChannel() {
        return guildChannel;
    }
}
