package org.dimas4ek.dawncord.event;

import org.dimas4ek.dawncord.entities.User;
import org.dimas4ek.dawncord.entities.guild.Guild;

public class GuildBanEvent implements Event {
    private final Guild guild;
    private final User user;

    public GuildBanEvent(Guild guild, User user) {
        this.guild = guild;
        this.user = user;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    public User getUser() {
        return user;
    }
}
