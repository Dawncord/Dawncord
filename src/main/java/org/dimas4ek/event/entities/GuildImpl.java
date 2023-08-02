package org.dimas4ek.event.entities;

import org.dimas4ek.api.ApiClient;
import org.dimas4ek.enities.guild.*;
import org.dimas4ek.enities.types.GuildChannelType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    public String getName() {
        return guild.getString("name");
    }
    
    @Override
    public GuildMember getOwner() {
        for (GuildMember member : getMembers()) {
            if (member.getUser().getId().equals(guild.getString("owner_id"))) {
                return member;
            }
        }
        return null;
    }
    
    @Override
    public List<GuildChannel> getChannels() {
        return getEntityList("/guilds/" + getId() + "/channels", GuildChannelImpl::new);
    }
    
    @Override
    public GuildChannel getChannelById(String id) {
        for (GuildChannel channel : getChannels()) {
            if (channel.getId().equals(id)) {
                return channel;
            }
        }
        return null;
    }
    
    @Override
    public List<GuildChannel> getChannelsByName(String name) {
        List<GuildChannel> channels = new ArrayList<>();
        for (GuildChannel channel : getChannels()) {
            if (channel.getName().equals(name)) {
                channels.add(channel);
            }
        }
        return channels;
    }
    
    @Override
    public List<GuildMember> getMembers() {
        return getEntityList("/guilds/" + getId() + "/members", (JSONObject member) -> new GuildMemberImpl(member, getId()));
    }
    
    @Override
    public List<GuildRole> getRoles() {
        return getEntityList("/guilds/" + getId() + "/roles", GuildRoleImpl::new);
    }
    
    @Override
    public List<GuildRole> getRolesByName(String roleName) {
        return getRoles().stream()
            .filter(role -> role.getName().equalsIgnoreCase(roleName))
            .collect(Collectors.toList());
    }
    
    @Override
    public GuildRole getRoleById(String id) {
        return getRoles().stream()
            .filter(role -> role.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    private <T> List<T> getEntityList(String url, Function<JSONObject, T> constructor) {
        try {
            JSONArray jsonArray = ApiClient.getApiResponseArray(url);
            List<T> list = new ArrayList<>();
            
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(constructor.apply(jsonArray.getJSONObject(i)));
            }
            
            return list;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void createChannel(String name, GuildChannelType type, GuildCategory category) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("type", type.getValue());
        if (category != null) {
            jsonObject.put("parent_id", category.getId());
        }
        ApiClient.postApiRequest("/guilds/" + getId() + "/channels", jsonObject);
    }
    
    @Override
    public void createTextChannel(String name) {
        createChannel(name, GuildChannelType.GUILD_TEXT, null);
    }
    
    @Override
    public void createTextChannel(String name, GuildCategory category) {
        createChannel(name, GuildChannelType.GUILD_TEXT, category);
    }
    
    @Override
    public void createVoiceChannel(String name) {
        createChannel(name, GuildChannelType.GUILD_VOICE, null);
    }
    
    @Override
    public void createVoiceChannel(String name, GuildCategory category) {
        createChannel(name, GuildChannelType.GUILD_VOICE, category);
    }
    
    @Override
    public void createForumChannel(String name) {
        createChannel(name, GuildChannelType.GUILD_FORUM, null);
    }
    
    @Override
    public void createForumChannel(String name, GuildCategory category) {
        createChannel(name, GuildChannelType.GUILD_FORUM, category);
    }
    
    @Override
    public void createStageChannel(String name) {
        createChannel(name, GuildChannelType.GUILD_STAGE_VOICE, null);
    }
    
    @Override
    public void createStageChannel(String name, GuildCategory category) {
        createChannel(name, GuildChannelType.GUILD_STAGE_VOICE, category);
    }
    
    @Override
    public void createNewsChannel(String name) {
        createChannel(name, GuildChannelType.GUILD_ANNOUNCEMENT, null);
    }
    
    @Override
    public void createNewsChannel(String name, GuildCategory category) {
        createChannel(name, GuildChannelType.GUILD_ANNOUNCEMENT, category);
    }
    
    @Override
    public void createCategory(String name) {
        createChannel(name, GuildChannelType.GUILD_CATEGORY, null);
    }
    
    @Override
    public GuildCategory getCategoryByName(String name) {
        return getCategory(channel -> channel.getName().equals(name));
    }
    
    @Override
    public GuildCategory getCategoryById(String id) {
        return getCategory(channel -> channel.getId().equals(id));
    }
    
    private GuildCategory getCategory(Predicate<GuildChannel> predicate) {
        return getChannels().stream()
            .filter(channel -> channel.getType().equals("CATEGORY") && predicate.test(channel))
            .map(GuildCategoryImpl::new)
            .findFirst()
            .orElse(null);
    }
}
