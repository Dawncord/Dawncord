package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.channel.Invite;
import io.github.dawncord.api.entities.guild.Guild;

/**
 * Represents an event related to invites in a guild.
 */
public class InviteEvent implements Event {
    private final Guild guild;
    private final Invite invite;

    /**
     * Constructs an InviteEvent with the specified guild and invite.
     *
     * @param guild  The guild associated with the event.
     * @param invite The invite involved in the event.
     */
    public InviteEvent(Guild guild, Invite invite) {
        this.guild = guild;
        this.invite = invite;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    /**
     * Retrieves the invite involved in the event.
     *
     * @return The invite involved in the event.
     */
    public Invite getInvite() {
        return invite;
    }
}
