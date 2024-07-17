package io.github.dawncord.api.entities.channel;

import io.github.dawncord.api.action.GuildChannelModifyAction;
import io.github.dawncord.api.action.GuildChannelPositionModifyAction;
import io.github.dawncord.api.action.InviteCreateAction;
import io.github.dawncord.api.action.WebhookCreateAction;
import io.github.dawncord.api.entities.PermissionOverride;
import io.github.dawncord.api.entities.Webhook;
import io.github.dawncord.api.entities.channel.thread.Thread;
import io.github.dawncord.api.event.CreateEvent;
import io.github.dawncord.api.event.ModifyEvent;

import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a channel within a guild.
 * Extends the Channel interface.
 */
public interface GuildChannel extends Channel {
    /**
     * Converts this guild channel to a TextChannel.
     *
     * @return The corresponding text channel.
     */
    TextChannel asText();

    /**
     * Converts this guild channel to a VoiceChannel.
     *
     * @return The corresponding voice channel.
     */
    VoiceChannel asVoice();

    /**
     * Converts this guild channel to a Thread.
     *
     * @return The corresponding thread.
     */
    Thread asThread();


    /**
     * Converts this guild channel to a GuildForum.
     *
     * @return The corresponding guild forum.
     */
    GuildForum asForum();

    /**
     * Modifies this guild channel.
     *
     * @param handler The consumer for the modification action.
     * @return A ModifyEvent for this guild channel.
     */
    ModifyEvent<GuildChannel> modify(Consumer<GuildChannelModifyAction> handler);

    /**
     * Retrieves the list of permissions overrides for this guild channel.
     *
     * @return The list of PermissionOverrides.
     */
    List<PermissionOverride> getPermissions();

    /**
     * Retrieves a PermissionOverride by role or user ID.
     *
     * @param id Role or user ID.
     * @return The permission override corresponding to the provided ID.
     */
    PermissionOverride getPermissionById(String id);

    /**
     * Retrieves a PermissionOverride by role or user ID.
     *
     * @param id Role or user ID.
     * @return The permission override corresponding to the provided ID.
     */
    PermissionOverride getPermissionById(long id);

    /**
     * Deletes a permission override by role or user ID.
     *
     * @param id Role or user ID.
     */
    void deletePermission(long id);

    /**
     * Retrieves the list of invites for this guild channel.
     *
     * @return The list of Invites.
     */
    List<Invite> getInvites();

    /**
     * Retrieves an Invite by its code.
     *
     * @param code The code of the invite to retrieve.
     * @return The invite corresponding to the provided code.
     */
    Invite getInvite(String code);

    /**
     * Creates a new invite for this guild channel.
     *
     * @param handler The consumer for the invite creation action.
     */
    void createInvite(Consumer<InviteCreateAction> handler);

    /**
     * Checks if this guild channel has active threads.
     *
     * @return True if there are active threads, false otherwise.
     */
    boolean hasActiveThreads();

    /**
     * Retrieves the count of active threads for this guild channel.
     *
     * @return The count of active threads.
     */
    int getActiveThreadsCount();

    /**
     * Retrieves the list of active threads for this guild channel.
     *
     * @return The list of Threads.
     */
    List<Thread> getActiveThreads();

    /**
     * Retrieves the list of public archive threads for this guild channel.
     *
     * @return The list of Threads.
     */
    List<Thread> getPublicArchiveThreads();

    /**
     * Retrieves the list of private archive threads for this guild channel.
     *
     * @return The list of Threads.
     */
    List<Thread> getPrivateArchiveThreads();

    /**
     * Retrieves the list of joined private archive threads for this guild channel.
     *
     * @return The list of Threads.
     */
    List<Thread> getJoinedPrivateArchiveThreads();

    /**
     * Modifies the position of this guild channel.
     *
     * @param handler The consumer for the position modification action.
     * @return A ModifyEvent for this guild channel's position.
     */
    ModifyEvent<GuildChannel> modifyPosition(Consumer<GuildChannelPositionModifyAction> handler);

    /**
     * Retrieves the list of webhooks for this guild channel.
     *
     * @return The list of Webhooks.
     */
    List<Webhook> getWebhooks();

    /**
     * Retrieves a Webhook by its ID.
     *
     * @param webhookId The ID of the webhook to retrieve.
     * @return The webhook corresponding to the provided ID.
     */
    Webhook getWebhookById(String webhookId);

    /**
     * Retrieves a Webhook by its ID.
     *
     * @param webhookId The ID of the webhook to retrieve.
     * @return The webhook corresponding to the provided ID.
     */
    Webhook getWebhookById(long webhookId);

    /**
     * Retrieves a Webhook by its name.
     *
     * @param webhookName The name of the webhook to retrieve.
     * @return The webhook corresponding to the provided name.
     */
    Webhook getWebhookByName(String webhookName);

    /**
     * Creates a new webhook for this guild channel.
     *
     * @param handler The consumer for the webhook creation action.
     * @return A CreateEvent for the newly created webhook.
     */
    CreateEvent<Webhook> createWebhook(Consumer<WebhookCreateAction> handler);
}