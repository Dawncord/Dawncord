package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.channel.Invite;
import io.github.dawncord.api.entities.guild.Guild;

/**
 * Represents an event related to invites in a guild.
 */
public record InviteEvent(Guild getGuild, Invite invite) implements Event {
    /**
     * Constructs an InviteEvent with the specified guild and invite.
     *
     * @param getGuild The guild associated with the event.
     * @param invite   The invite involved in the event.
     */
    public InviteEvent {
    }

    /**
     * Retrieves the invite involved in the event.
     *
     * @return The invite involved in the event.
     */
    @Override
    public Invite invite() {
        return invite;
    }
}
