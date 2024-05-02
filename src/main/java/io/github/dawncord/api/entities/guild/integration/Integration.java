package io.github.dawncord.api.entities.guild.integration;

import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.guild.role.GuildRole;
import io.github.dawncord.api.types.IntegrationExpireBehavior;
import io.github.dawncord.api.types.IntegrationType;
import io.github.dawncord.api.types.Scope;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Represents an integration of a third-party service with a guild.
 */
public interface Integration extends ISnowflake {
    /**
     * Gets the name of the integration.
     *
     * @return The name of the integration.
     */
    String getName();

    /**
     * Gets the type of the integration.
     *
     * @return The type of the integration.
     */
    IntegrationType getType();

    /**
     * Gets the account associated with the integration.
     *
     * @return The account associated with the integration.
     */
    IntegrationAccount getAccount();

    /**
     * Gets the application associated with the integration.
     *
     * @return The application associated with the integration.
     */
    IntegrationApplication getApplication();

    /**
     * Gets the scopes of the integration.
     *
     * @return The scopes of the integration.
     */
    List<Scope> getScopes();

    /**
     * Gets the user associated with the integration.
     *
     * @return The user associated with the integration.
     */
    User getUser();

    /**
     * Gets the role associated with the integration.
     *
     * @return The role associated with the integration.
     */
    GuildRole getRole();

    /**
     * Gets the expiration behavior of the integration.
     *
     * @return The expiration behavior of the integration.
     */
    IntegrationExpireBehavior getExpireBehavior();

    /**
     * Gets the timestamp when the integration was last synced.
     *
     * @return The timestamp when the integration was last synced.
     */
    ZonedDateTime getSyncedTimestamp();

    /**
     * Checks if the integration is enabled.
     *
     * @return true if the integration is enabled, false otherwise.
     */
    boolean isEnabled();

    /**
     * Checks if the integration is currently syncing.
     *
     * @return true if the integration is syncing, false otherwise.
     */
    boolean isSyncing();

    /**
     * Checks if emoticons are enabled for the integration.
     *
     * @return true if emoticons are enabled, false otherwise.
     */
    boolean isEmoticonsEnabled();

    /**
     * Checks if the integration has been revoked.
     *
     * @return true if the integration has been revoked, false otherwise.
     */
    boolean isRevoked();

    /**
     * Gets the grace period before expiration of the integration.
     *
     * @return The grace period before expiration of the integration.
     */
    int getExpireGrace();

    /**
     * Gets the subscriber count of the integration.
     *
     * @return The subscriber count of the integration.
     */
    int getSubscriberCount();

    /**
     * Deletes the integration.
     */
    void delete();
}
