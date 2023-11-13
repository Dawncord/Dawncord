package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.json.JSONObject;

public class CurrentMemberModifyAction {
    private final String guildId;
    private final JSONObject jsonObject;

    public CurrentMemberModifyAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = new JSONObject();
    }

    public CurrentMemberModifyAction setNickname(String nickname) {
        jsonObject.put("nick", nickname);
        return this;
    }

    public void submit() {
        ApiClient.patch(jsonObject, "/guilds/" + guildId + "/members/@me");
        jsonObject.clear();
    }
}
