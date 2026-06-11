package io.github.dawncord.api.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.GuildChannelPositionModifyAction;
import io.github.dawncord.api.action.InviteCreateAction;
import io.github.dawncord.api.action.guildchannel.GuildChannelModifyAction;
import io.github.dawncord.api.action.webhook.WebhookCreateAction;
import io.github.dawncord.api.entities.PermissionOverride;
import io.github.dawncord.api.entities.Webhook;
import io.github.dawncord.api.entities.channel.thread.Thread;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.event.CreateEvent;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.types.ChannelType;
import io.github.dawncord.api.types.PermissionOverrideType;
import io.github.dawncord.api.types.PermissionType;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a channel within a guild.
 */
public class GuildChannel extends Channel {
    private final JsonNode channel;
    private final Guild guild;

    /**
     * Constructs a new GuildChannel instance.
     *
     * @param channel The JSON node representing the guild channel.
     * @param guild   The guild to which this channel belongs.
     */
    public GuildChannel(JsonNode channel, Guild guild) {
        super(channel, guild);
        this.channel = channel;
        this.guild = guild;
    }

    public TextChannel asText() {
        return new TextChannel(channel, guild);
    }

    public VoiceChannel asVoice() {
        return new VoiceChannel(channel, guild);
    }

    public Thread asThread() {
        return new Thread(channel, guild);
    }

    public GuildForum asForum() {
        return new GuildForum(channel, guild);
    }

    public ModifyEvent<GuildChannel> modify(Consumer<GuildChannelModifyAction> handler) {
        ActionExecutor.modifyChannel(handler, this);
        return new ModifyEvent<>(guild.getChannelById(getId()));
    }

    public List<PermissionOverride> getPermissions() {
        return JsonUtils.getEntityList(channel.get("permission_overwrites"), override ->
                new PermissionOverride(
                        override.get("id").asText(),
                        getPermissionType(override.get("type").asInt()),
                        getDenied(override.get("deny").asText()),
                        getAllowed(override.get("allow").asText())
                )
        );
    }

    public PermissionOverride getPermissionById(String permissionId) {
        return getPermissions().stream().filter(permission -> permission.id().equals(permissionId)).findAny().orElse(null);
    }

    public PermissionOverride getPermissionById(long permissionId) {
        return getPermissionById(String.valueOf(permissionId));
    }

    public void editPermission(String permissionId, PermissionOverrideType type, List<PermissionType> denied, List<PermissionType> allowed) {
        long denyValue = 0;
        long allowValue = 0;
        for (PermissionType deny : denied) {
            denyValue |= deny.getValue();
        }
        for (PermissionType allow : allowed) {
            allowValue |= allow.getValue();
        }
        ObjectNode jsonObject = JsonNodeFactory.instance.objectNode()
                .put("type", type.getValue())
                .put("deny", String.valueOf(denyValue))
                .put("allow", String.valueOf(allowValue));

        ApiClient.put(jsonObject, Routes.Channel.Permission(getId(), permissionId));

    }

    public void deletePermission(long permissionId) {
        ApiClient.delete(Routes.Channel.Permission(getId(), String.valueOf(permissionId)));
    }

    public List<Invite> getInvites() {
        return (getType() != ChannelType.PUBLIC_THREAD || getType() != ChannelType.PRIVATE_THREAD || getType() != ChannelType.ANNOUNCEMENT_THREAD)
                ? JsonUtils.getEntityList(JsonUtils.fetch(Routes.Channel.Invite.All(getId())), invite -> new Invite(invite, guild))
                : null;
    }

    public Invite getInvite(String code) {
        return getInvites().stream().filter(invite -> invite.getCode().equals(code)).findAny().orElse(null);
    }

    public void createInvite(Consumer<InviteCreateAction> handler) {
        ActionExecutor.createChannelInvite(handler, this);
    }

    public boolean hasActiveThreads() {
        return !getActiveThreads().isEmpty();
    }

    public int getActiveThreadsCount() {
        return getActiveThreads().size();
    }

    public List<Thread> getActiveThreads() {
        return getGuild().getActiveThreads().stream().filter(thread -> thread.getChannel().equals(this)).toList();
    }

    public List<Thread> getPublicArchiveThreads() {
        return (getType() != ChannelType.PUBLIC_THREAD || getType() != ChannelType.PRIVATE_THREAD || getType() != ChannelType.ANNOUNCEMENT_THREAD)
                ? JsonUtils.getEntityList(JsonUtils.fetch(Routes.Channel.Thread.Archive.Public(getId())).get("threads"), thread -> new Thread(thread, guild))
                : null;
    }

    public List<Thread> getPrivateArchiveThreads() {
        return getType() != ChannelType.PUBLIC_THREAD || getType() != ChannelType.PRIVATE_THREAD || getType() != ChannelType.ANNOUNCEMENT_THREAD
                ? JsonUtils.getEntityList(JsonUtils.fetch(Routes.Channel.Thread.Archive.Private(getId())).get("threads"), thread -> new Thread(thread, guild))
                : null;
    }

    public List<Thread> getJoinedPrivateArchiveThreads() {
        return getType() != ChannelType.PUBLIC_THREAD || getType() != ChannelType.PRIVATE_THREAD || getType() != ChannelType.ANNOUNCEMENT_THREAD
                ? JsonUtils.getEntityList(JsonUtils.fetch(Routes.Channel.Thread.Archive.JoinedPrivate(getId(), "@me")).get("threads"), thread -> new Thread(thread, guild))
                : null;
    }

    public ModifyEvent<GuildChannel> modifyPosition(Consumer<GuildChannelPositionModifyAction> handler) {
        ActionExecutor.modifyChannelPosition(handler, getGuild().getId(), getId());
        return new ModifyEvent<>(guild.getChannelById(getId()));
    }

    public List<Webhook> getWebhooks() {
        return getType() != ChannelType.PUBLIC_THREAD || getType() != ChannelType.PRIVATE_THREAD || getType() != ChannelType.ANNOUNCEMENT_THREAD
                ? JsonUtils.getEntityList(JsonUtils.fetch(Routes.Channel.Webhooks(getId())).get("webhooks"), webhook -> new Webhook(webhook, guild))
                : null;
    }

    public Webhook getWebhookById(String webhookId) {
        return getWebhooks().stream().filter(webhook -> webhook.getId().equals(webhookId)).findAny().orElse(null);
    }

    public Webhook getWebhookById(long webhookId) {
        return getWebhookById(String.valueOf(webhookId));
    }

    public Webhook getWebhookByName(String webhookName) {
        return getWebhooks().stream().filter(webhook -> webhook.getName().equals(webhookName)).findAny().orElse(null);
    }

    public CreateEvent<Webhook> createWebhook(Consumer<WebhookCreateAction> handler) {
        String id = ActionExecutor.createWebhook(handler, getId());
        return new CreateEvent<>(guild.getWebhookById(id));
    }

    /**
     * Converts an integer representation of permission override type to its corresponding enum value.
     *
     * @param type The integer representation of permission override type.
     * @return The corresponding {@link PermissionOverrideType} enum value.
     */
    private PermissionOverrideType getPermissionType(int type) {
        return EnumUtils.getEnumObjectFromInt(type, PermissionOverrideType.class);
    }

    /**
     * Retrieves the denied permissions from the provided bitfield.
     *
     * @param deny The bitfield representing denied permissions.
     * @return A list of {@link PermissionType} representing denied permissions.
     */
    private List<PermissionType> getDenied(String deny) {
        List<PermissionType> deniedPermissions = new ArrayList<>();

        for (PermissionType perm : PermissionType.values()) {
            if ((Long.parseLong(deny) & perm.getValue()) != 0) {
                deniedPermissions.add(perm);
            }
        }

        return deniedPermissions;
    }

    /**
     * Retrieves the allowed permissions from the provided bitfield.
     *
     * @param allow The bitfield representing allowed permissions.
     * @return A list of {@link PermissionType} representing allowed permissions.
     */
    private List<PermissionType> getAllowed(String allow) {
        List<PermissionType> allowedPermissions = new ArrayList<>();

        for (PermissionType perm : PermissionType.values()) {
            if ((Long.parseLong(allow) & perm.getValue()) != 0) {
                allowedPermissions.add(perm);
            }
        }

        return allowedPermissions;
    }
}
