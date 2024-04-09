package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.entities.channel.Invite;
import org.dimas4ek.wrapper.entities.guild.Guild;

public class InviteEvent implements Event {
    private final Guild guild;
    private final Invite invite;

    public InviteEvent(Guild guild, Invite invite) {
        this.guild = guild;
        this.invite = invite;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    public Invite getInvite() {
        return invite;
    }
}
