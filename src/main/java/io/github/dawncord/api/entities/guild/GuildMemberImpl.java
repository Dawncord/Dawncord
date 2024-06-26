package io.github.dawncord.api.entities.guild;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.GuildMemberModifyAction;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.UserImpl;
import io.github.dawncord.api.entities.guild.role.GuildRole;
import io.github.dawncord.api.entities.image.Avatar;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.types.GuildMemberFlag;
import io.github.dawncord.api.types.PermissionType;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.TimeUtils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Represents an implementation of a guild member.
 */
public class GuildMemberImpl implements GuildMember {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final JsonNode member;
    private final Guild guild;

    /**
     * Constructs a new GuildMemberImpl with the provided member data and guild.
     *
     * @param member The JSON data representing the guild member.
     * @param guild  The guild to which the member belongs.
     */
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
                : new Avatar(getUser().getId(), member.get("user").get("avatar").asText());
    }

    @Override
    public List<GuildMemberFlag> getFlags() {
        return EnumUtils.getEnumListFromLong(member, "flags", GuildMemberFlag.class);
    }

    @Override
    public ZonedDateTime getTimeJoined() {
        return TimeUtils.getZonedDateTime(member, "joined_at");
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
    public boolean hasPermission(PermissionType permission) {
        return getPermissions().contains(permission);
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
    public GuildRole getRoleById(String roleId) {
        for (GuildRole role : getRoles()) {
            if (role.getId().equals(roleId)) {
                return role;
            }
        }
        return null;
    }

    @Override
    public GuildRole getRoleById(long roleId) {
        return getRoleById(String.valueOf(roleId));
    }

    @Override
    public void modifyRoles(List<GuildRole> roles) {
        List<String> roleIds = roles.stream().map(GuildRole::getId).toList();
        ObjectNode jsonObject = mapper.createObjectNode()
                .set("roles", mapper.valueToTree(roleIds));
        ApiClient.put(jsonObject, Routes.Guild.Member.Get(guild.getId(), getUser().getId()));
    }

    @Override
    public void removeRoleById(String roleId) {
        ApiClient.delete(Routes.Guild.Member.Role(guild.getId(), getUser().getId(), roleId));
    }

    @Override
    public void removeRoleById(long roleId) {
        removeRoleById(String.valueOf(roleId));
    }

    @Override
    public void removeRoleByName(String roleName) {
        getRolesByName(roleName).forEach(role -> removeRoleById(role.getId()));
    }

    @Override
    public boolean isPending() {
        return member.has("pending") && member.get("pending").asBoolean();
    }

    @Override
    public boolean isOwner() {
        return getUser().getId().equals(guild.getOwner().getId());
    }

    @Override
    public boolean isBoosting() {
        return member.has("premium_since") && !member.hasNonNull("premium_since");
    }

    @Override
    public boolean isMuted() {
        return member.has("mute") && member.get("mute").asBoolean();
    }

    @Override
    public boolean isDeafened() {
        return member.has("deaf") && member.get("deaf").asBoolean();
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
    public void setTimeout(ZonedDateTime timeout) {
        ObjectNode jsonObject = mapper.createObjectNode()
                .put("communication_disabled_until", timeout.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        ApiClient.patch(jsonObject, Routes.Guild.Member.Get(guild.getId(), getUser().getId()));
    }

    @Override
    public void removeTimeout() {
        ObjectNode jsonObject = mapper.createObjectNode()
                .set("communication_disabled_until", NullNode.instance);
        ApiClient.patch(jsonObject, Routes.Guild.Member.Get(guild.getId(), getUser().getId()));
    }

    @Override
    public void mute(boolean mute) {
        ApiClient.patch(mapper.createObjectNode().put("mute", mute), Routes.Guild.Member.Get(getGuild().getId(), getUser().getId()));
    }

    @Override
    public void deaf(boolean deaf) {
        ApiClient.patch(mapper.createObjectNode().put("deaf", deaf), Routes.Guild.Member.Get(getGuild().getId(), getUser().getId()));
    }

    @Override
    public String getAsMention() {
        return "<@" + getUser().getId() + ">";
    }
}
