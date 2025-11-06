package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.automod.AutoModRule;

/**
 * Represents an event related to auto-moderation in a guild.
 */
public class AutoModEvent implements Event {

    private final Guild guild;
    private final AutoModRule rule;

    /**
     * Constructs an AutoModEvent with the specified guild and auto-moderation rule.
     *
     * @param guild The guild associated with the event.
     * @param rule  The auto-moderation rule associated with the event.
     */
    public AutoModEvent(Guild guild, AutoModRule rule) {
        this.guild = guild;
        this.rule = rule;
    }

    /**
     * Retrieves the auto-moderation rule associated with the event.
     *
     * @return The auto-moderation rule.
     */
    public AutoModRule getRule() {
        return rule;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }
}
