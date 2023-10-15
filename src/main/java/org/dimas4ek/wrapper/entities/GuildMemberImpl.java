package org.dimas4ek.wrapper.entities;

import org.json.JSONObject;

public class GuildMemberImpl implements GuildMember {
    private final JSONObject guildMember;

    public GuildMemberImpl(JSONObject guildMember) {
        this.guildMember = guildMember;
    }

    @Override
    public String getNickname() {
        return guildMember.getString("nick");
    }
}
