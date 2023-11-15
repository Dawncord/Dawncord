package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.json.JSONObject;

public class CurrentMemberModifyAction {
    private final String guildId;
    private final JSONObject jsonObject;
    private boolean hasChanges = false;

    public CurrentMemberModifyAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = new JSONObject();
    }

    public CurrentMemberModifyAction setNickname(String nickname) {
        jsonObject.put("nick", nickname);
        hasChanges = true;
        return this;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, "/guilds/" + guildId + "/members/@me");
            hasChanges = false;
        }
        jsonObject.clear();
    }
}
