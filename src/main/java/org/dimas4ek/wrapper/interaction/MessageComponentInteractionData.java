package org.dimas4ek.wrapper.interaction;

import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildMember;

public class MessageComponentInteractionData implements InteractionData {
    private final Interaction interaction;
    private final Guild guild;
    private final GuildChannel guildChannel;
    private final GuildMember guildMember;
    private final String customId;
    private final String id;

    public MessageComponentInteractionData(Interaction interaction, Guild guild, GuildChannel guildChannel, GuildMember guildMember, String customId, String id) {
        this.interaction = interaction;
        this.guild = guild;
        this.guildChannel = guildChannel;
        this.guildMember = guildMember;
        this.customId = customId;
        this.id = id;
    }

    @Override
    public Interaction getInteraction() {
        return interaction;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    public GuildChannel getGuildChannel() {
        return guildChannel;
    }

    @Override
    public GuildMember getGuildMember() {
        return guildMember;
    }

    public String getCustomId() {
        return customId;
    }

    public String getId() {
        return id;
    }
}
