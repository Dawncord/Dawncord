package org.dimas4ek.dawncord.entities.channel;

import org.dimas4ek.dawncord.action.GuildChannelModifyAction;
import org.dimas4ek.dawncord.action.GuildChannelPositionModifyAction;
import org.dimas4ek.dawncord.action.InviteCreateAction;
import org.dimas4ek.dawncord.action.WebhookCreateAction;
import org.dimas4ek.dawncord.entities.PermissionOverride;
import org.dimas4ek.dawncord.entities.Webhook;
import org.dimas4ek.dawncord.entities.channel.thread.Thread;
import org.dimas4ek.dawncord.event.CreateEvent;
import org.dimas4ek.dawncord.event.ModifyEvent;
import org.dimas4ek.dawncord.types.PermissionOverrideType;
import org.dimas4ek.dawncord.types.PermissionType;

import java.util.List;
import java.util.function.Consumer;

public interface GuildChannel extends Channel {
    TextChannel asText();

    VoiceChannel asVoice();

    Thread asThread();

    GuildForum asForum();

    ModifyEvent<GuildChannel> modify(Consumer<GuildChannelModifyAction> handler);

    List<PermissionOverride> getPermissions();

    PermissionOverride getPermissionById(String permissionId);

    PermissionOverride getPermissionById(long permissionId);

    void editPermission(String permissionId, PermissionOverrideType type, List<PermissionType> denied, List<PermissionType> allowed);

    void deletePermission(long permissionId);

    List<Invite> getInvites();

    Invite getInvite(String code);

    void createInvite(Consumer<InviteCreateAction> handler);

    boolean hasActiveThreads();

    int getActiveThreadsCount();

    List<Thread> getActiveThreads();

    List<Thread> getPublicArchiveThreads();

    List<Thread> getPrivateArchiveThreads();

    List<Thread> getJoinedPrivateArchiveThreads();

    ModifyEvent<GuildChannel> modifyPosition(Consumer<GuildChannelPositionModifyAction> handler);

    List<Webhook> getWebhooks();

    Webhook getWebhookById(String webhookId);

    Webhook getWebhookById(long webhookId);

    Webhook getWebhookByName(String webhookName);

    CreateEvent<Webhook> createWebhook(Consumer<WebhookCreateAction> handler);
}