package org.dimas4ek.dawncord.entities.guild.integration;

import org.dimas4ek.dawncord.entities.ISnowflake;

public class IntegrationAccount implements ISnowflake {
    private final String id;
    private final String name;

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

    public String getName() {
        return name;
    }
}
