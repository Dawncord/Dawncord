package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.integration.Integration;

public class IntegrationEvent implements Event {
    private final Guild guild;
    private final Integration integration;

    public IntegrationEvent(Guild guild, Integration integration) {
        this.guild = guild;
        this.integration = integration;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    public Integration getIntegration() {
        return integration;
    }
}
