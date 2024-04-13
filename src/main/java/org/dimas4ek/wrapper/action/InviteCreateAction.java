package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.types.ChannelType;
import org.dimas4ek.wrapper.types.TargetType;

public class InviteCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final GuildChannel channel;
    private boolean hasChanges = false;

    public InviteCreateAction(GuildChannel channel) {
        this.channel = channel;
        this.jsonObject = mapper.createObjectNode();
    }

    private InviteCreateAction setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    public InviteCreateAction setMaxAge(int age) {
        return setProperty("max_age", age);

    }

    public InviteCreateAction setMaxUses(int uses) {
        return setProperty("max_uses", uses);
        
    }

    public InviteCreateAction setTemporary(boolean temporary) {
        return setProperty("temporary", temporary);
        
    }

    public InviteCreateAction setUnique(boolean unique) {
        return setProperty("unique", unique);
        
    }

    public InviteCreateAction setTargetType(TargetType targetType) {
        if (channel.getType() == ChannelType.GUILD_VOICE) {
            setProperty("target_type", targetType.getValue());
        }
        return this;
    }

    public InviteCreateAction setTargetUser(TargetType targetType, int targetUserId) {
        setProperty("target_type", targetType.getValue());
        setProperty("target_user_id", targetUserId);
        return this;
    }

    public InviteCreateAction setTargetApplication(TargetType targetType, int targetApplicationId) {
        setProperty("target_type", targetType.getValue());
        setProperty("target_application_id", targetApplicationId);
        return this;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.post(jsonObject, "/channels/" + channel.getId() + "/invites");
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
