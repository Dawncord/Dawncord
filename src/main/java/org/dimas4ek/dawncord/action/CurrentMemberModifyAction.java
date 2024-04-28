package org.dimas4ek.dawncord.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.dawncord.ApiClient;
import org.dimas4ek.dawncord.Routes;

public class CurrentMemberModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private boolean hasChanges = false;

    public CurrentMemberModifyAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = mapper.createObjectNode();
    }

    public CurrentMemberModifyAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    public CurrentMemberModifyAction setNickname(String nickname) {
        return setProperty("nick", nickname);
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.CurrentMember(guildId));
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
