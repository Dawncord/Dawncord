package org.dimas4ek.dawncord.entities.guild.integration;

import org.dimas4ek.dawncord.entities.ISnowflake;
import org.dimas4ek.dawncord.entities.User;
import org.dimas4ek.dawncord.entities.guild.role.GuildRole;
import org.dimas4ek.dawncord.types.IntegrationExpireBehavior;
import org.dimas4ek.dawncord.types.IntegrationType;
import org.dimas4ek.dawncord.types.Scope;

import java.time.ZonedDateTime;
import java.util.List;

public interface Integration extends ISnowflake {
    String getName();

    IntegrationType getType();

    IntegrationAccount getAccount();

    IntegrationApplication getApplication();

    List<Scope> getScopes();

    User getUser();

    GuildRole getRole();

    IntegrationExpireBehavior getExpireBehavior();

    ZonedDateTime getSyncedTimestamp();

    boolean isEnabled();

    boolean isSyncing();

    boolean isEmoticonsEnabled();

    boolean isRevoked();

    int getExpireGrace();

    int getSubscriberCount();

    void delete();
}
