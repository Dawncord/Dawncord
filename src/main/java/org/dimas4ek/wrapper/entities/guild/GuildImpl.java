package org.dimas4ek.wrapper.entities.guild;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.*;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.GuildChannelImpl;
import org.dimas4ek.wrapper.entities.role.GuildRole;
import org.dimas4ek.wrapper.entities.role.GuildRoleImpl;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class GuildImpl implements Guild {
    private final JSONObject guild;

    public GuildImpl(JSONObject guild) {
        this.guild = guild;
    }

    @Override
    public String getId() {
        return guild.getString("id");
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        return guild.getString("name");
    }

    @Override
    public String getDescription() {
        return guild.getString("description");
    }

    @Override
    public User getOwner() {
        JSONObject user = ApiClient.getJsonObject("/users/" + guild.getString("owner_id"));
        return new UserImpl(user);
    }

    @Override
    public List<GuildChannel> getChannels() {
        JSONArray channels = ApiClient.getJsonArray("/guilds/" + getId() + "/channels");
        return JsonUtils.getEntityList(channels, GuildChannelImpl::new);
    }

    @Override
    public GuildChannel getChannelById(String channelId) {
        JSONObject channel = ApiClient.getJsonObject("/channels/" + channelId);
        return new GuildChannelImpl(channel);
    }

    @Override
    public GuildChannel getChannelById(long channelId) {
        JSONObject channel = ApiClient.getJsonObject("/channels/" + channelId);
        return new GuildChannelImpl(channel);
    }

    @Override
    public List<GuildMember> getMembers() {
        JSONArray members = ApiClient.getJsonArrayParams(
                "/guilds/" + getId() + "/members",
                Map.of("limit", "100")
        );
        return JsonUtils.getEntityList(members, guildMember -> new GuildMemberImpl(guild, guildMember));
    }

    @Override
    public GuildMember getMemberById(String memberId) {
        JSONObject member = ApiClient.getJsonObject("/guilds/" + getId() + "/members/" + memberId);
        return new GuildMemberImpl(guild, member);
    }

    @Override
    public GuildMember getMemberById(long memberId) {
        return getMemberById(String.valueOf(memberId));
    }

    @Override
    public List<GuildMember> searchMembers(String username, int limit) {
        JSONArray members = ApiClient.getJsonArrayParams(
                "/guilds/" + getId() + "/members/search",
                Map.of(
                        "query", username,
                        "limit", String.valueOf(limit)
                )
        );

        return JsonUtils.getEntityList(members, guildMember -> new GuildMemberImpl(guild, guildMember));
    }

    @Override
    public List<GuildMember> searchMembers(String username) {
        return searchMembers(username, 1);
    }

    @Override
    public List<GuildBan> getBans() {
        JSONArray bans = ApiClient.getJsonArray("/guilds/" + getId() + "/bans");
        return JsonUtils.getEntityList(bans, GuildBanImpl::new);
    }

    @Override
    public GuildBan getBanByUserId(String userId) {
        return new GuildBanImpl(ApiClient.getJsonObject("/guilds/" + getId() + "/bans/" + userId));
    }

    @Override
    public GuildBan getBanByUserId(long userId) {
        return getBanByUserId(String.valueOf(userId));
    }

    @Override
    public List<GuildRole> getRoles() {
        JSONArray roles = ApiClient.getJsonArray("/guilds/" + getId() + "/roles");
        return JsonUtils.getEntityList(roles, GuildRoleImpl::new);
    }

    @Override
    public GuildRole getRoleById(String roleId) {
        return getRoles().stream().filter(role -> role.getId().equals(roleId)).findAny().orElse(null);
    }

    @Override
    public GuildRole getRoleById(long roleId) {
        return getRoleById(String.valueOf(roleId));
    }

    @Override
    public GuildRole getPublicRole() {
        return getRoleById(getId());
    }

    @Override
    public List<GuildRole> getRolesByName(String roleName) {
        return getRoles().stream().filter(role -> role.getName().equals(roleName)).toList();
    }
}
