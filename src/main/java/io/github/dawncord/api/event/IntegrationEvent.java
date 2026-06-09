package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.integration.Integration;

/**
 * Represents an event related to integrations in a guild.
 */
public record IntegrationEvent(Guild getGuild, Integration integration) implements Event {
    /**
     * Constructs an IntegrationEvent with the specified guild and integration.
     *
     * @param getGuild    The guild associated with the event.
     * @param integration The integration involved in the event.
     */
    public IntegrationEvent {
    }

    /**
     * Retrieves the integration involved in the event.
     *
     * @return The integration involved in the event.
     */
    @Override
    public Integration integration() {
        return integration;
    }
}
