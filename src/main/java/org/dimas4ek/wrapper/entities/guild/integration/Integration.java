package org.dimas4ek.wrapper.entities.guild.integration;

import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.role.GuildRole;
import org.dimas4ek.wrapper.types.IntegrationExpireBehavior;
import org.dimas4ek.wrapper.types.IntegrationType;
import org.dimas4ek.wrapper.types.Scope;

import java.time.ZonedDateTime;
import java.util.List;

public interface Integration {
    String getId();

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
