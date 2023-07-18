package org.dimas4ek.event.interaction;

import org.dimas4ek.api.ApiClient;
import org.dimas4ek.enities.guild.Guild;
import org.dimas4ek.enities.guild.GuildChannel;
import org.dimas4ek.enities.guild.GuildMember;
import org.dimas4ek.enities.guild.GuildRole;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuildInteraction implements Guild {
    private final JSONObject guild;
    
    public GuildInteraction(JSONObject guild) {
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
                channelList.add(new GuildChannelInteraction(channel));
            }
            
            return channelList;
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public List<GuildMember> getMembers() {
        try {
            JSONArray memberData = ApiClient.getApiResponseArray("/guilds/" + getId() + "/members");
            
            List<GuildMember> memberList = new ArrayList<>();
            
            for (int i = 0; i < memberData.length(); i++) {
                JSONObject member = memberData.getJSONObject(i);
                memberList.add(new GuildMemberInteraction(member, getId()));
            }
            
            return memberList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    @Override
    public List<GuildRole> getRoles() {
        try {
            JSONArray serverRoleData = ApiClient.getApiResponseArray("/guilds/" + getId() + "/roles");
            List<GuildRole> roleList = new ArrayList<>();
            
            for (int i = 0; i < serverRoleData.length(); i++) {
                JSONObject serverRole = serverRoleData.getJSONObject(i);
                roleList.add(new GuildRoleInteraction(serverRole));
            }
            
            return roleList;
        } catch (IOException | JSONException e) {
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
                    roleList.add(new GuildRoleInteraction(serverRole));
                }
            }
            
            return roleList;
        } catch (IOException | JSONException e) {
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
                    return new GuildRoleInteraction(serverRole);
                }
            }
            
            return null;
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
