package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.automod.AutoModRule;

/**
 * Represents an event related to auto-moderation in a guild.
 */
public record AutoModEvent(Guild guild, AutoModRule rule) implements Event {

    /**
     * Constructs an AutoModEvent with the specified guild and auto-moderation rule.
     *
     * @param guild The guild associated with the event.
     * @param rule  The auto-moderation rule associated with the event.
     */
    public AutoModEvent {
    }

    /**
     * Retrieves the auto-moderation rule associated with the event.
     *
     * @return The auto-moderation rule.
     */
    @Override
    public AutoModRule rule() {
        return rule;
    }
}
