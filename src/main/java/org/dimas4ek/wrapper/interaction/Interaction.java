package org.dimas4ek.wrapper.interaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.dimas4ek.wrapper.entities.Guild;
import org.dimas4ek.wrapper.entities.GuildChannel;

@Getter
@RequiredArgsConstructor
public class Interaction {
    private final String interactionId;
    private final String interactionToken;
    private final Guild guild;
    private final GuildChannel guildChannel;
}
