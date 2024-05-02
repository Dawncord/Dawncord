package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.channel.Invite;
import io.github.dawncord.api.types.ChannelType;
import io.github.dawncord.api.types.TargetType;

/**
 * Represents an action for creating an invite.
 *
 * @see Invite
 */
public class InviteCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final GuildChannel channel;
    private boolean hasChanges = false;

    /**
     * Create a new {@link InviteCreateAction}
     *
     * @param channel The guild channel for which the invite is being created.
     */
    public InviteCreateAction(GuildChannel channel) {
        this.channel = channel;
        this.jsonObject = mapper.createObjectNode();
    }

    private InviteCreateAction setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets the maximum age for the invite.
     *
     * @param age the maximum age of the invite
     * @return the modified InviteCreateAction object with the maximum age set
     */
    public InviteCreateAction setMaxAge(int age) {
        return setProperty("max_age", age);
    }

    /**
     * Sets the maximum number of uses for the invite.
     *
     * @param uses the maximum number of uses for the invite
     * @return the modified InviteCreateAction object with the maximum number of uses set
     */
    public InviteCreateAction setMaxUses(int uses) {
        return setProperty("max_uses", uses);
    }

    /**
     * Sets the temporary property for the invite.
     *
     * @param temporary the value to set the temporary property to
     * @return the modified InviteCreateAction object
     */
    public InviteCreateAction setTemporary(boolean temporary) {
        return setProperty("temporary", temporary);
    }

    /**
     * Sets the unique property for the invite.
     *
     * @param unique the value to set the unique property to
     * @return the modified InviteCreateAction object
     */
    public InviteCreateAction setUnique(boolean unique) {
        return setProperty("unique", unique);
    }

    /**
     * Sets the target type for the invite if the channel type is GUILD_VOICE.
     *
     * @param targetType the target type to set
     * @return the modified InviteCreateAction object
     */
    public InviteCreateAction setTargetType(TargetType targetType) {
        if (channel.getType() == ChannelType.GUILD_VOICE) {
            setProperty("target_type", targetType.getValue());
        }
        return this;
    }

    /**
     * Sets the target user for the invite.
     *
     * @param targetType   the type of target user
     * @param targetUserId the ID of the target user
     * @return the modified InviteCreateAction object
     */
    public InviteCreateAction setTargetUser(TargetType targetType, int targetUserId) {
        setProperty("target_type", targetType.getValue());
        setProperty("target_user_id", targetUserId);
        return this;
    }

    /**
     * Sets the target application for the invite.
     *
     * @param targetType          the type of target application
     * @param targetApplicationId the ID of the target application
     * @return the modified InviteCreateAction object
     */
    public InviteCreateAction setTargetApplication(TargetType targetType, int targetApplicationId) {
        setProperty("target_type", targetType.getValue());
        setProperty("target_application_id", targetApplicationId);
        return this;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.post(jsonObject, Routes.Channel.Invite.All(channel.getId()));
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
