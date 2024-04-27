package org.dimas4ek.dawncord.entities.guild;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.ApiClient;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.action.GuildMemberModifyAction;
import org.dimas4ek.dawncord.entities.User;
import org.dimas4ek.dawncord.entities.UserImpl;
import org.dimas4ek.dawncord.entities.guild.role.GuildRole;
import org.dimas4ek.dawncord.entities.image.Avatar;
import org.dimas4ek.dawncord.event.ModifyEvent;
import org.dimas4ek.dawncord.types.GuildMemberFlag;
import org.dimas4ek.dawncord.types.PermissionType;
import org.dimas4ek.dawncord.utils.ActionExecutor;
import org.dimas4ek.dawncord.utils.EnumUtils;
import org.dimas4ek.dawncord.utils.MessageUtils;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class GuildMemberImpl implements GuildMember {
    private final JsonNode member;
    private final Guild guild;
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
        return new UserImpl(member.get("user"));
    }

    @Override
    public String getNickname() {
        return member.has("nick") && member.hasNonNull("nick")
                ? member.get("nick").asText()
                : null;
    }

    @Override
    public Avatar getAvatar() {
        return member.has("avatar") && member.hasNonNull("avatar")
                ? new Avatar(guild.getId(), getUser().getId(), member.get("avatar").asText())
                : null;
    }

    @Override
    public List<GuildMemberFlag> getFlags() {
        return EnumUtils.getEnumListFromLong(member, "flags", GuildMemberFlag.class);
    }

    @Override
    public ZonedDateTime getTimeJoined() {
        return MessageUtils.getZonedDateTime(member, "joined_at");
    }

    @Override
    public Set<PermissionType> getPermissions() {
        Set<PermissionType> permissions = new HashSet<>();
        if (isOwner()) {
            permissions.addAll(List.of(PermissionType.values()));
        } else {
            permissions.addAll(guild.getPublicRole().getPermissions());
            getRoles().forEach(role -> permissions.addAll(role.getPermissions()));
        }
        return permissions;
    }

    @Override
    public List<GuildRole> getRoles() {
        List<GuildRole> roles = new ArrayList<>();
        if (!member.get("roles").isEmpty()) {
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
        ApiClient.delete(Routes.Guild.Member.Role(guild.getId(), getUser().getId(), roleId));
    }

    @Override
    public void deleteRoleById(long roleId) {
        deleteRoleById(String.valueOf(roleId));
    }

    @Override
    public boolean isPending() {
        if (member.has("pending")) {
            isPending = member.get("pending").asBoolean();
        }
        return isPending != null && isPending;
    }

    @Override
    public boolean isOwner() {
        return getUser().getId().equals(guild.getOwner().getId());
    }

    @Override
    public boolean isBoosting() {
        if (member.has("premium_since")) {
            isBoosting = !member.hasNonNull("premium_since");
        }
        return isBoosting != null && isBoosting;
    }

    @Override
    public boolean isMuted() {
        if (member.has("mute")) {
            isMuted = member.get("mute").asBoolean();
        }
        return isMuted != null && isMuted;
    }

    @Override
    public boolean isDeafened() {
        if (member.has("deaf")) {
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
        ApiClient.put(null, Routes.Guild.Member.Get(guildId, getUser().getId()));
    }

    @Override
    public void addToGuild(long guildId) {
        addToGuild(String.valueOf(guildId));
    }

    @Override
    public void addRole(String roleId) {
        ApiClient.put(null, Routes.Guild.Member.Role(getGuild().getId(), getUser().getId(), roleId));
    }

    @Override
    public void addRole(long roleId) {
        addRole(String.valueOf(roleId));
    }

    @Override
    public void removeRole(String roleId) {
        ApiClient.delete(Routes.Guild.Member.Role(getGuild().getId(), getUser().getId(), roleId));
    }

    @Override
    public void removeRole(long roleId) {
        removeRole(String.valueOf(roleId));
    }

    @Override
    public ModifyEvent<GuildMember> modify(Consumer<GuildMemberModifyAction> handler) {
        ActionExecutor.modifyGuildMember(handler, getGuild().getId(), getUser().getId());
        return new ModifyEvent<>(guild.getMemberById(getUser().getId()));
    }

    @Override
    public String getAsMention() {
        return "<@" + getUser().getId() + ">";
    }
}
