package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.action.GuildChannelModifyAction;
import org.dimas4ek.wrapper.action.GuildChannelPositionModifyAction;
import org.dimas4ek.wrapper.action.InviteCreateAction;
import org.dimas4ek.wrapper.action.WebhookCreateAction;
import org.dimas4ek.wrapper.entities.PermissionOverride;
import org.dimas4ek.wrapper.entities.Webhook;
import org.dimas4ek.wrapper.entities.WebhookImpl;
import org.dimas4ek.wrapper.entities.thread.Thread;
import org.dimas4ek.wrapper.entities.thread.ThreadImpl;
import org.dimas4ek.wrapper.types.PermissionOverrideType;
import org.dimas4ek.wrapper.types.PermissionType;
import org.dimas4ek.wrapper.utils.ActionExecutor;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GuildChannelImpl extends ChannelImpl implements GuildChannel {
    private final JSONObject channel;

    public GuildChannelImpl(JSONObject channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public TextChannel asText() {
        return new TextChannelImpl(channel);
    }

    @Override
    public VoiceChannel asVoice() {
        return new VoiceChannelImpl(channel);
    }

    @Override
    public Thread asThread() {
        return new ThreadImpl(channel);
    }

    @Override
    public GuildForum asForum() {
        return new GuildForumImpl(channel);
    }

    @Override
    public void modify(Consumer<GuildChannelModifyAction> handler) {
        ActionExecutor.modifyChannel(handler, this);
    }

    @Override
    public List<PermissionOverride> getPermissions() {
        return JsonUtils.getEntityList(channel.getJSONArray("permission_overwrites"), (JSONObject t) ->
                new PermissionOverride(
                        t.getString("id"),
                        getPermissionType(t.getInt("type")),
                        getDenied(t.getString("deny")),
                        getAllowed(t.getString("allow"))
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
        JSONObject jsonObject = new JSONObject()
                .put("type", type)
                .put("deny", String.valueOf(denyValue))
                .put("allow", String.valueOf(allowValue));

        ApiClient.put(jsonObject, "/channels/" + getId() + "/permissions/" + permissionId);
        jsonObject.clear();
    }

    @Override
    public void deletePermission(int permissionId) {
        ApiClient.delete("/channels/" + getId() + "/permissions/" + permissionId);
    }

    @Override
    public List<Invite> getInvites() {
        return JsonUtils.getEntityList(JsonUtils.fetchArray("/channels/" + getId() + "/invites"), InviteImpl::new);
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
        return JsonUtils.getEntityList(JsonUtils.fetchEntity("/channels/" + getId() + "/threads/archived/public").getJSONArray("threads"), ThreadImpl::new);
    }

    @Override
    public List<Thread> getPrivateArchiveThreads() {
        return JsonUtils.getEntityList(JsonUtils.fetchEntity("/channels/" + getId() + "/threads/archived/private").getJSONArray("threads"), ThreadImpl::new);
    }

    @Override
    public List<Thread> getJoinedPrivateArchiveThreads() {
        return JsonUtils.getEntityList(JsonUtils.fetchEntity("/channels/" + getId() + "/users/@me/threads/archived/private").getJSONArray("threads"), ThreadImpl::new);
    }

    @Override
    public void modifyPosition(Consumer<GuildChannelPositionModifyAction> handler) {
        ActionExecutor.modifyChannelPosition(handler, getGuild().getId(), getId());
    }

    @Override
    public List<Webhook> getChannelWebhooks() {
        return JsonUtils.getEntityList(JsonUtils.fetchEntity("/channels/" + getId() + "/webhooks").getJSONArray("webhooks"), WebhookImpl::new);
    }

    @Override
    public Webhook getWebhookById(String webhookId) {
        return getChannelWebhooks().stream().filter(webhook -> webhook.getId().equals(webhookId)).findAny().orElse(null);
    }

    @Override
    public Webhook getWebhookById(long webhookId) {
        return getWebhookById(String.valueOf(webhookId));
    }

    @Override
    public Webhook getChannelWebhookByName(String webhookName) {
        return getChannelWebhooks().stream().filter(webhook -> webhook.getName().equals(webhookName)).findAny().orElse(null);
    }

    @Override
    public void createWebhook(Consumer<WebhookCreateAction> handler) {
        ActionExecutor.createWebhook(handler, getId());
    }

    private PermissionOverrideType getPermissionType(int type) {
        return EnumUtils.getEnumObjectFromInt(type, PermissionOverrideType.class);
        /*for (PermissionOverrideType permissionOverrideType : PermissionOverrideType.values()) {
            if (type == permissionOverrideType.getValue()) {
                return permissionOverrideType;
            }
        }
        return null;*/
    }

    private List<PermissionType> getDenied(String deny) {
        List<PermissionType> deniedPermissions = new ArrayList<>();

        for (PermissionType perm : PermissionType.values()) {
            if ((Long.parseLong(deny) & perm.getValue()) != 0) {
                deniedPermissions.add(perm);
            }
        }

        return deniedPermissions;
    }

    private List<PermissionType> getAllowed(String allow) {
        List<PermissionType> deniedPermissions = new ArrayList<>();

        for (PermissionType perm : PermissionType.values()) {
            if ((Long.parseLong(allow) & perm.getValue()) != 0) {
                deniedPermissions.add(perm);
            }
        }

        return deniedPermissions;
    }
}
