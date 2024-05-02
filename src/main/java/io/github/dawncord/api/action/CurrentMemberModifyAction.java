package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.User;

/**
 * Represents an action for updating a current member.
 *
 * @see User
 */
public class CurrentMemberModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private boolean hasChanges = false;

    /**
     * Create a new {@link CurrentMemberModifyAction}
     *
     * @param guildId The ID of the guild whose current member is being modified.
     */
    public CurrentMemberModifyAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = mapper.createObjectNode();
    }

    private CurrentMemberModifyAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets the nickname for the current member.
     *
     * @param nickname the nickname to set
     * @return the modified CurrentMemberModifyAction object
     */
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
