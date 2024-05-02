package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.integration.Integration;

/**
 * Represents an event related to integrations in a guild.
 */
public class IntegrationEvent implements Event {
    private final Guild guild;
    private final Integration integration;

    /**
     * Constructs an IntegrationEvent with the specified guild and integration.
     *
     * @param guild       The guild associated with the event.
     * @param integration The integration involved in the event.
     */
    public IntegrationEvent(Guild guild, Integration integration) {
        this.guild = guild;
        this.integration = integration;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    /**
     * Retrieves the integration involved in the event.
     *
     * @return The integration involved in the event.
     */
    public Integration getIntegration() {
        return integration;
    }
}
