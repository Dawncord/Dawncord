package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.action.ChannelModifyAction;
import org.dimas4ek.wrapper.entities.PermissionOverride;
import org.dimas4ek.wrapper.entities.thread.Thread;
import org.dimas4ek.wrapper.entities.thread.ThreadImpl;
import org.dimas4ek.wrapper.types.PermissionOverrideType;
import org.dimas4ek.wrapper.types.PermissionType;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    public ChannelModifyAction modify() {
        return new ChannelModifyAction(this);
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
    public List<Invite> getInvites() {
        return JsonUtils.getEntityList(JsonUtils.fetchArray("/channels/" + getId() + "/invites"), InviteImpl::new);
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
