package io.github.dawncord.api.entities.guild.integration;

import io.github.dawncord.api.entities.ISnowflake;

/**
 * Represents an account associated with an integration.
 */
public class IntegrationAccount implements ISnowflake {
    private final String id;
    private final String name;

    /**
     * Constructs a new IntegrationAccount with the given ID and name.
     *
     * @param id   The ID of the integration account.
     * @param name The name of the integration account.
     */
    public IntegrationAccount(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    /**
     * Gets the name of the integration account.
     *
     * @return The name of the integration account.
     */
    public String getName() {
        return name;
    }
}
