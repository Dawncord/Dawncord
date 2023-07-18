package org.dimas4ek.event.interaction;

import org.dimas4ek.api.ApiClient;
import org.dimas4ek.enities.GuildMember;
import org.dimas4ek.enities.GuildRole;
import org.dimas4ek.enities.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuildMemberInteraction implements GuildMember {
    private final JSONObject member;
    private final String guildId;
    
    public GuildMemberInteraction(JSONObject member, String guildId) {
        this.member = member;
        this.guildId = guildId;
    }
    
    @Override
    public User getUser() {
        return new UserInteraction(member.getJSONObject("user"));
    }
    
    @Override
    public String getNickname() {
        return member.getString("nick");
    }
    
    @Override
    public List<GuildRole> getRoles() {
        try {
            JSONArray serverRoleData = ApiClient.getApiResponseArray("/guilds/" + guildId + "/roles");
            JSONArray memberRoleData = member.getJSONArray("roles");
            List<GuildRole> roleList = new ArrayList<>();
            
            for (int i = 0; i < serverRoleData.length(); i++) {
                JSONObject serverRole = serverRoleData.getJSONObject(i);
                for (int j = 0; j < memberRoleData.length(); j++) {
                    String memberRole = memberRoleData.getString(j);
                    if (memberRole.equals(serverRole.getString("id"))) {
                        roleList.add(new GuildRoleInteraction(serverRole));
                    }
                }
            }
            
            return roleList;
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
