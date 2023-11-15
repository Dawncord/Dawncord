package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.types.ChannelType;
import org.dimas4ek.wrapper.types.TargetType;
import org.json.JSONObject;

public class InviteCreateAction {
    private final JSONObject jsonObject;
    private final GuildChannel channel;
    private boolean hasChanges = false;

    public InviteCreateAction(GuildChannel channel) {
        this.channel = channel;
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String key, Object value) {
        jsonObject.put(key, value);
        hasChanges = true;
    }

    public InviteCreateAction setMaxAge(int age) {
        setProperty("max_age", age);
        return this;
    }

    public InviteCreateAction setMaxUses(int uses) {
        setProperty("max_uses", uses);
        return this;
    }

    public InviteCreateAction setTemporary(boolean temporary) {
        setProperty("temporary", temporary);
        return this;
    }

    public InviteCreateAction setUnique(boolean unique) {
        setProperty("unique", unique);
        return this;
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
        jsonObject.clear();
    }
}
