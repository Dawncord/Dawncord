package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.entities.role.GuildRole;
import org.dimas4ek.wrapper.types.GuildMemberFlag;
import org.dimas4ek.wrapper.types.PermissionType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public List<String> getFlags() {
        if (guildMember.has("flags") || guildMember.getInt("flags") != 0) {
            List<String> flags = new ArrayList<>();
            long flagsFromJson = Long.parseLong(String.valueOf(guildMember.getInt("flags")));
            for (GuildMemberFlag flag : GuildMemberFlag.values()) {
                if ((flagsFromJson & flag.getValue()) != 0) {
                    flags.add(flag.name());
                }
            }
            return flags;
        }
        return Collections.emptyList();
    }

    @Override
    public ZonedDateTime getTimeJoined() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return ZonedDateTime.parse(guildMember.getString("joined_at"), formatter);
    }

    @Override
    public List<String> getPermissions() {
        List<String> permissions = new ArrayList<>();
        long permissionsFromJson = Long.parseLong("");
        for (PermissionType permission : PermissionType.values()) {
            if ((permissionsFromJson & permission.getValue()) != 0) {
                permissions.add(permission.name());
            }
        }
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
    public User getUser() {
        return new UserImpl(guildMember.getJSONObject("user"));
    }
}
