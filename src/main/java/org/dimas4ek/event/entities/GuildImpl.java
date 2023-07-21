package org.dimas4ek.event.entities;

import org.dimas4ek.api.ApiClient;
import org.dimas4ek.enities.guild.*;
import org.dimas4ek.enities.types.GuildChannelType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
        try {
            JSONArray channelData = ApiClient.getApiResponseArray("/guilds/" + getId() + "/channels");
            List<GuildChannel> channelList = new ArrayList<>();
            
            for (int i = 0; i < channelData.length(); i++) {
                JSONObject channel = channelData.getJSONObject(i);
                channelList.add(new GuildChannelImpl(channel));
            }
            
            return channelList;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public List<GuildMember> getMembers() {
        JSONArray memberData = ApiClient.getApiResponseArray("/guilds/" + getId() + "/members");
        
        List<GuildMember> memberList = new ArrayList<>();
        
        for (int i = 0; i < memberData.length(); i++) {
            JSONObject member = memberData.getJSONObject(i);
            memberList.add(new GuildMemberImpl(member, getId()));
        }
        
        return memberList;
        
    }
    
    @Override
    public List<GuildRole> getRoles() {
        try {
            JSONArray serverRoleData = ApiClient.getApiResponseArray("/guilds/" + getId() + "/roles");
            List<GuildRole> roleList = new ArrayList<>();
            
            for (int i = 0; i < serverRoleData.length(); i++) {
                JSONObject serverRole = serverRoleData.getJSONObject(i);
                roleList.add(new GuildRoleImpl(serverRole));
            }
            
            return roleList;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public List<GuildRole> getRolesByName(String roleName) {
        try {
            JSONArray serverRoleData = ApiClient.getApiResponseArray("/guilds/" + getId() + "/roles");
            List<GuildRole> roleList = new ArrayList<>();
            
            for (int i = 0; i < serverRoleData.length(); i++) {
                JSONObject serverRole = serverRoleData.getJSONObject(i);
                if (serverRole.getString("name").equals(roleName)) {
                    roleList.add(new GuildRoleImpl(serverRole));
                }
            }
            
            return roleList;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public GuildRole getRoleById(String id) {
        try {
            JSONArray serverRoleData = ApiClient.getApiResponseArray("/guilds/" + getId() + "/roles");
            
            for (int i = 0; i < serverRoleData.length(); i++) {
                JSONObject serverRole = serverRoleData.getJSONObject(i);
                if (serverRole.getString("id").equals(id)) {
                    return new GuildRoleImpl(serverRole);
                }
            }
            
            return null;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void createTextChannel(String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("type", GuildChannelType.GUILD_TEXT.getValue());
        ApiClient.postApiRequest("/guilds/" + getId() + "/channels", jsonObject);
    }
    
    @Override
    public void createTextChannel(String name, GuildCategory category) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("type", GuildChannelType.GUILD_TEXT.getValue());
        jsonObject.put("parent_id", category.getId());
        ApiClient.postApiRequest("/guilds/" + getId() + "/channels", jsonObject);
    }
    
    @Override
    public GuildCategory getCategoryByName(String name) {
        for (GuildChannel channel : getChannels()) {
            if (channel.getName().equals(name) && channel.getType().equals("CATEGORY")) {
                return new GuildCategoryImpl(channel);
            }
        }
        return null;
    }
    
    @Override
    public GuildCategory getCategoryById(String id) {
        for (GuildChannel channel : getChannels()) {
            if (channel.getId().equals(id) && channel.getType().equals("CATEGORY")) {
                return new GuildCategoryImpl(channel);
            }
        }
        return null;
    }
}
