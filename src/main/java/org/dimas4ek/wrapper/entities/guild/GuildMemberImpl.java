package org.dimas4ek.wrapper.entities.guild;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.action.GuildMemberModifyAction;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.guild.role.GuildRole;
import org.dimas4ek.wrapper.entities.image.Avatar;
import org.dimas4ek.wrapper.types.GuildMemberFlag;
import org.dimas4ek.wrapper.types.PermissionType;
import org.dimas4ek.wrapper.utils.ActionExecutor;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.MessageUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class GuildMemberImpl implements GuildMember {
    private final JSONObject guild;
    private final JSONObject guildMember;

    public GuildMemberImpl(JSONObject guild, JSONObject guildMember) {
        this.guild = guild;
        this.guildMember = guildMember;
    }

    @Override
    public String getNickname() {
        return !guildMember.isNull("nick")
                ? guildMember.getString("nick")
                : getUser().getUsername();
    }

    @Override
    public Avatar getAvatar() {
        return !guildMember.isNull("avatar")
                ? new Avatar(getGuild().getId(), getUser().getId(), guildMember.getString("avatar"))
                : getUser().getAvatar();
    }

    @Override
    public List<GuildMemberFlag> getFlags() {
        return EnumUtils.getEnumListFromLong(guildMember, "flags", GuildMemberFlag.class);
        /*if (guildMember.has("flags") || guildMember.getInt("flags") != 0) {
            List<String> flags = new ArrayList<>();
            long flagsFromJson = Long.parseLong(String.valueOf(guildMember.getInt("flags")));
            for (GuildMemberFlag flag : GuildMemberFlag.values()) {
                if ((flagsFromJson & flag.getValue()) != 0) {
                    flags.add(flag.name());
                }
            }
            return flags;
        }
        return Collections.emptyList();*/
    }

    @Override
    public ZonedDateTime getTimeJoined() {
        return MessageUtils.getZonedDateTime(guildMember, "joined_at");
    }

    @Override
    public Set<PermissionType> getPermissions() {
        Set<PermissionType> permissions = new HashSet<>();
        if (isOwner()) {
            permissions.addAll(List.of(PermissionType.values()));
        }
        permissions.addAll(getGuild().getPublicRole().getPermissions());
        getRoles().forEach(role -> permissions.addAll(role.getPermissions()));
        return permissions;
    }

    @Override
    public List<GuildRole> getRoles() {
        List<GuildRole> memberRoles = new ArrayList<>();
        List<String> rolesString = getGuild().getRoles().stream().map(GuildRole::getId).toList();
        JSONArray rolesJson = guildMember.getJSONArray("roles");
        for (int i = 0; i < rolesJson.length(); i++) {
            String roleId = rolesJson.getString(i);
            if (rolesString.contains(roleId)) {
                memberRoles.add(getGuild().getRoleById(roleId));
            }
        }
        return memberRoles;
    }

    @Override
    public List<GuildRole> getRolesByName(String roleName) {
        return getRoles().stream().filter(role -> role.getName().equals(roleName)).toList();
    }

    @Override
    public void deleteRoleById(String roleId) {
        ApiClient.delete("/guilds/" + getGuild().getId() + "/members/" + getUser().getId() + "/roles/" + roleId);
    }

    @Override
    public void deleteRoleById(long roleId) {
        deleteRoleById(String.valueOf(roleId));
    }

    @Override
    public boolean isPending() {
        return guildMember.getBoolean("pending");
    }

    @Override
    public boolean isOwner() {
        return getUser().getId().equals(getGuild().getOwner().getId());
    }

    @Override
    public boolean isBoosting() {
        return !guildMember.isNull("premium_since");
    }

    @Override
    public boolean isMuted() {
        return guildMember.getBoolean("mute");
    }

    @Override
    public boolean isDeafened() {
        return guildMember.getBoolean("deaf");
    }

    @Override
    public Guild getGuild() {
        return new GuildImpl(guild);
    }

    @Override
    public void addToGuild(String guildId) {
        ApiClient.put(null, "/guilds/" + guildId + "/members/" + getUser().getId());
    }

    @Override
    public void addToGuild(long guildId) {
        addToGuild(String.valueOf(guildId));
    }

    @Override
    public User getUser() {
        return new UserImpl(guildMember.getJSONObject("user"));
    }

    @Override
    public void addRole(String roleId) {
        ApiClient.put(null, "/guilds/" + getGuild().getId() + "/members/" + getUser().getId() + "/roles/" + roleId);
    }

    @Override
    public void addRole(long roleId) {
        addRole(String.valueOf(roleId));
    }

    @Override
    public void removeRole(String roleId) {
        ApiClient.delete("/guilds/" + getGuild().getId() + "/members/" + getUser().getId() + "/roles/" + roleId);
    }

    @Override
    public void removeRole(long roleId) {
        removeRole(String.valueOf(roleId));
    }

    @Override
    public void modify(Consumer<GuildMemberModifyAction> handler) {
        ActionExecutor.modifyGuildMember(handler, getGuild().getId(), getUser().getId());
    }

    @Override
    public String getAsMention() {
        return "<@" + getUser().getId() + ">";
    }
}
