package io.github.dawncord.api.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.GuildChannelModifyAction;
import io.github.dawncord.api.action.GuildChannelPositionModifyAction;
import io.github.dawncord.api.action.InviteCreateAction;
import io.github.dawncord.api.action.WebhookCreateAction;
import io.github.dawncord.api.entities.PermissionOverride;
import io.github.dawncord.api.entities.Webhook;
import io.github.dawncord.api.entities.WebhookImpl;
import io.github.dawncord.api.entities.channel.thread.Thread;
import io.github.dawncord.api.entities.channel.thread.ThreadImpl;
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
 * Represents an implementation of a guild channel.
 */
public class GuildChannelImpl extends ChannelImpl implements GuildChannel {
    private final JsonNode channel;
    private final Guild guild;

    /**
     * Constructs a new GuildChannelImpl instance.
     *
     * @param channel The JSON node representing the guild channel.
     * @param guild   The guild to which this channel belongs.
     */
    public GuildChannelImpl(JsonNode channel, Guild guild) {
        super(channel, guild);
        this.channel = channel;
        this.guild = guild;
    }

    @Override
    public TextChannel asText() {
        return new TextChannelImpl(channel, guild);
    }

    @Override
    public VoiceChannel asVoice() {
        return new VoiceChannelImpl(channel, guild);
    }

    @Override
    public Thread asThread() {
        return new ThreadImpl(channel, guild);
    }

    @Override
    public GuildForum asForum() {
        return new GuildForumImpl(channel, guild);
    }

    @Override
    public ModifyEvent<GuildChannel> modify(Consumer<GuildChannelModifyAction> handler) {
        ActionExecutor.modifyChannel(handler, this);
        return new ModifyEvent<>(guild.getChannelById(getId()));
    }

    @Override
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

    @Override
    public PermissionOverride getPermissionById(String permissionId) {
        return getPermissions().stream().filter(permission -> permission.getId().equals(permissionId)).findAny().orElse(null);
    }

    @Override
    public PermissionOverride getPermissionById(long permissionId) {
        return getPermissionById(String.valueOf(permissionId));
    }

    @Override
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
        jsonObject.removeAll();
    }

    @Override
    public void deletePermission(long permissionId) {
        ApiClient.delete(Routes.Channel.Permission(getId(), String.valueOf(permissionId)));
    }

    @Override
    public List<Invite> getInvites() {
        return (getType() != ChannelType.PUBLIC_THREAD || getType() != ChannelType.PRIVATE_THREAD || getType() != ChannelType.ANNOUNCEMENT_THREAD)
                ? JsonUtils.getEntityList(JsonUtils.fetch(Routes.Channel.Invite.All(getId())), invite -> new InviteImpl(invite, guild))
                : null;
    }

    @Override
    public Invite getInvite(String code) {
        return getInvites().stream().filter(invite -> invite.getCode().equals(code)).findAny().orElse(null);
    }

    @Override
    public void createInvite(Consumer<InviteCreateAction> handler) {
        ActionExecutor.createChannelInvite(handler, this);
    }

    @Override
    public boolean hasActiveThreads() {
        return !getActiveThreads().isEmpty();
    }

    @Override
    public int getActiveThreadsCount() {
        return getActiveThreads().size();
    }

    @Override
    public List<Thread> getActiveThreads() {
        return getGuild().getActiveThreads().stream().filter(thread -> thread.getChannel().equals(this)).toList();
    }

    @Override
    public List<Thread> getPublicArchiveThreads() {
        return (getType() != ChannelType.PUBLIC_THREAD || getType() != ChannelType.PRIVATE_THREAD || getType() != ChannelType.ANNOUNCEMENT_THREAD)
                ? JsonUtils.getEntityList(JsonUtils.fetch(Routes.Channel.Thread.Archive.Public(getId())).get("threads"), thread -> new ThreadImpl(thread, guild))
                : null;
    }

    @Override
    public List<Thread> getPrivateArchiveThreads() {
        return getType() != ChannelType.PUBLIC_THREAD || getType() != ChannelType.PRIVATE_THREAD || getType() != ChannelType.ANNOUNCEMENT_THREAD
                ? JsonUtils.getEntityList(JsonUtils.fetch(Routes.Channel.Thread.Archive.Private(getId())).get("threads"), thread -> new ThreadImpl(thread, guild))
                : null;
    }

    @Override
    public List<Thread> getJoinedPrivateArchiveThreads() {
        return getType() != ChannelType.PUBLIC_THREAD || getType() != ChannelType.PRIVATE_THREAD || getType() != ChannelType.ANNOUNCEMENT_THREAD
                ? JsonUtils.getEntityList(JsonUtils.fetch(Routes.Channel.Thread.Archive.JoinedPrivate(getId(), "@me")).get("threads"), thread -> new ThreadImpl(thread, guild))
                : null;
    }

    @Override
    public ModifyEvent<GuildChannel> modifyPosition(Consumer<GuildChannelPositionModifyAction> handler) {
        ActionExecutor.modifyChannelPosition(handler, getGuild().getId(), getId());
        return new ModifyEvent<>(guild.getChannelById(getId()));
    }

    @Override
    public List<Webhook> getWebhooks() {
        return getType() != ChannelType.PUBLIC_THREAD || getType() != ChannelType.PRIVATE_THREAD || getType() != ChannelType.ANNOUNCEMENT_THREAD
                ? JsonUtils.getEntityList(JsonUtils.fetch(Routes.Channel.Webhooks(getId())).get("webhooks"), webhook -> new WebhookImpl(webhook, guild))
                : null;
    }

    @Override
    public Webhook getWebhookById(String webhookId) {
        return getWebhooks().stream().filter(webhook -> webhook.getId().equals(webhookId)).findAny().orElse(null);
    }

    @Override
    public Webhook getWebhookById(long webhookId) {
        return getWebhookById(String.valueOf(webhookId));
    }

    @Override
    public Webhook getWebhookByName(String webhookName) {
        return getWebhooks().stream().filter(webhook -> webhook.getName().equals(webhookName)).findAny().orElse(null);
    }

    @Override
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
