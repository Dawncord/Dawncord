package org.dimas4ek.wrapper.entities.guild;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.action.GuildModifyAction;
import org.dimas4ek.wrapper.entities.*;
import org.dimas4ek.wrapper.entities.channel.*;
import org.dimas4ek.wrapper.entities.role.GuildRole;
import org.dimas4ek.wrapper.entities.role.GuildRoleImpl;
import org.dimas4ek.wrapper.entities.thread.Thread;
import org.dimas4ek.wrapper.entities.thread.ThreadImpl;
import org.dimas4ek.wrapper.types.ChannelType;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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
        return new UserImpl(JsonUtils.fetchEntity("/users/" + guild.getString("owner_id")));
    }

    @Override
    public List<GuildChannel> getChannels() {
        return JsonUtils.getEntityList(JsonUtils.fetchArray("/guilds/" + getId() + "/channels"), GuildChannelImpl::new);
    }

    @Override
    public GuildChannel getChannelById(String channelId) {
        return new GuildChannelImpl(JsonUtils.fetchEntity("/channels/" + channelId));
    }

    @Override
    public GuildChannel getChannelById(long channelId) {
        return new GuildChannelImpl(JsonUtils.fetchEntity("/channels/" + channelId));
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
        return new GuildMemberImpl(guild, JsonUtils.fetchEntity("/guilds/" + getId() + "/members/" + memberId));
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
        return JsonUtils.getEntityList(JsonUtils.fetchArray("/guilds/" + getId() + "/bans"), GuildBanImpl::new);
    }

    @Override
    public GuildBan getBanByUserId(String userId) {
        return new GuildBanImpl(JsonUtils.fetchEntity("/guilds/" + getId() + "/bans/" + userId));
    }

    @Override
    public GuildBan getBanByUserId(long userId) {
        return getBanByUserId(String.valueOf(userId));
    }

    @Override
    public List<GuildRole> getRoles() {
        return JsonUtils.getEntityList(JsonUtils.fetchArray("/guilds/" + getId() + "/roles"), GuildRoleImpl::new);
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

    @Override
    public List<GuildCategory> getCategories() {
        List<GuildCategory> categories = new ArrayList<>();
        for (Channel channel : getChannels()) {
            if (channel.getType() == ChannelType.GUILD_CATEGORY) {
                categories.add(new GuildCategoryImpl(JsonUtils.fetchEntity("/channels/" + channel.getId())));
            }
        }
        return categories;
    }

    @Override
    public GuildCategory getCategoryById(String categoryId) {
        return getCategories().stream().filter(category -> category.getId().equals(categoryId)).findAny().orElse(null);
    }

    @Override
    public GuildCategory getCategoryById(long categoryId) {
        return getCategoryById(String.valueOf(categoryId));
    }

    @Override
    public GuildModifyAction modify() {
        return new GuildModifyAction(this);
    }

    @Override
    public void delete() {
        ApiClient.delete("/guilds/" + getId());
    }

    @Override
    public List<Thread> getActiveThreads() {
        return JsonUtils.getEntityList(JsonUtils.fetchEntity("/guilds/" + getId() + "/threads/active").getJSONArray("threads"), ThreadImpl::new);
    }

    @Override
    public Thread getThreadById(String threadId) {
        return getActiveThreads().stream().filter(thread -> thread.getId().equals(threadId)).findAny().orElse(null);
    }

    @Override
    public Thread getThreadById(long threadId) {
        return getThreadById(String.valueOf(threadId));
    }
}
