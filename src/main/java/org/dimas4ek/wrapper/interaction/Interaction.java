package org.dimas4ek.wrapper.interaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.dimas4ek.wrapper.entities.GuildMember;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;

@Getter
@RequiredArgsConstructor
public class Interaction {
    private final String interactionId;
    private final String interactionToken;
    private final GuildMember guildMember;
    private final Guild guild;
    private final GuildChannel guildChannel;
}
