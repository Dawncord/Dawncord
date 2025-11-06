package io.github.dawncord.api.entities.guild.integration;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.utils.LazyLoader;

/**
 * Represents an account associated with an integration.
 */
public class IntegrationAccount implements ISnowflake {
    private final LazyLoader loader;
    private String id;
    private String name;

    /**
     * Constructs a new IntegrationAccount with the given ID and name.
     *
     */
    public IntegrationAccount(JsonNode account) {
        loader = new LazyLoader(account);
    }

    @Override
    public String getId() {
        id = loader.loadString(id, "id");
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
        name = loader.loadString(name, "name");
        return name;
    }
}
