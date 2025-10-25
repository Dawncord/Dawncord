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
public class CurrentMemberModifyAction extends Action<CurrentMemberModifyAction> {
    private final String guildId;

    /**
     * Create a new {@link CurrentMemberModifyAction}
     *
     * @param guildId The ID of the guild whose current member is being modified.
     */
    public CurrentMemberModifyAction(String guildId) {
        this.guildId = guildId;
        super();
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

    @Override
    protected void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.CurrentMember(guildId));
            hasChanges = false;
        }
    }
}
