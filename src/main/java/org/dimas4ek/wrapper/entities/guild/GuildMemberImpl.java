package org.dimas4ek.wrapper.entities.guild;

import com.fasterxml.jackson.databind.JsonNode;
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

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class GuildMemberImpl implements GuildMember {
    private final JsonNode member;
    private final Guild guild;
    private User user;
    private String nick;
    private Avatar avatar;
    private List<GuildMemberFlag> flags;
    private ZonedDateTime joinedAt;
    private Boolean isOwner;
    private List<GuildRole> roles = new ArrayList<>();
    private Set<PermissionType> permissions = new HashSet<>();
    private Boolean isPending;
    private Boolean isBoosting;
    private Boolean isMuted;
    private Boolean isDeafened;

    public GuildMemberImpl(JsonNode member, Guild guild) {
        this.member = member;
        this.guild = guild;
    }

    @Override
    public User getUser() {
        if (user == null) {
            user = new UserImpl(member.get("user"));
        }
        return user;
    }

    @Override
    public String getNickname() {
        if (nick == null) {
            nick = member.has("nick") && member.hasNonNull("nick")
                    ? member.get("nick").asText()
                    : null;
        }
        return nick;
    }

    @Override
    public Avatar getAvatar() {
        if (avatar == null) {
            avatar = member.has("avatar") && member.hasNonNull("avatar")
                    ? new Avatar(guild.getId(), getUser().getId(), member.get("avatar").asText())
                    : null;
        }
        return avatar;
    }

    @Override
    public List<GuildMemberFlag> getFlags() {
        if (flags == null) {
            flags = EnumUtils.getEnumListFromLong(member, "flags", GuildMemberFlag.class);
        }
        return flags;
    }

    @Override
    public ZonedDateTime getTimeJoined() {
        if (joinedAt == null) {
            joinedAt = MessageUtils.getZonedDateTime(member, "joined_at");
        }
        return joinedAt;
    }

    @Override
    public Set<PermissionType> getPermissions() {
        if (permissions == null) {
            permissions = new HashSet<>();
            if (isOwner()) {
                permissions.addAll(List.of(PermissionType.values()));
            } else {
                permissions.addAll(guild.getPublicRole().getPermissions());
                getRoles().forEach(role -> permissions.addAll(role.getPermissions()));
            }
        }
        return permissions;
    }

    @Override
    public List<GuildRole> getRoles() {
        if (roles == null && !member.get("roles").isEmpty()) {
            roles = new ArrayList<>();
            for (String guildRole : guild.getRoles().stream().map(GuildRole::getId).toList()) {
                for (JsonNode memberRole : member.get("roles")) {
                    if (memberRole.asText().equals(guildRole)) {
                        roles.add(guild.getRoleById(guildRole));
                    }
                }
            }
        }
        return roles;
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
        if (isPending == null && member.has("pending")) {
            isPending = member.get("pending").asBoolean();
        }
        return isPending != null && isPending;
    }

    @Override
    public boolean isOwner() {
        if (isOwner == null) {
            isOwner = getUser().getId().equals(guild.getOwner().getId());
        }
        return isOwner;
    }

    @Override
    public boolean isBoosting() {
        if (isBoosting == null && member.has("premium_since")) {
            isBoosting = !member.hasNonNull("premium_since");
        }
        return isBoosting != null && isBoosting;
    }

    @Override
    public boolean isMuted() {
        if (isMuted == null && member.has("mute")) {
            isMuted = member.get("mute").asBoolean();
        }
        return isMuted != null && isMuted;
    }

    @Override
    public boolean isDeafened() {
        if (isDeafened == null && member.has("deaf")) {
            isDeafened = member.get("deaf").asBoolean();
        }
        return isDeafened != null && isDeafened;
    }

    @Override
    public Guild getGuild() {
        return guild;
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
