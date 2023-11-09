package org.dimas4ek.wrapper.entities.guild.automod;

import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.types.AutoModEventType;
import org.dimas4ek.wrapper.types.AutoModTriggerType;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AutoModRuleImpl implements AutoModRule {
    private final JSONObject autoMod;

    public AutoModRuleImpl(JSONObject autoMod) {
        this.autoMod = autoMod;
    }

    @Override
    public String getId() {
        return autoMod.getString("id");
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        return autoMod.getString("name");
    }

    @Override
    public Guild getGuild() {
        return new GuildImpl(JsonUtils.fetchEntity("/guilds/" + autoMod.getString("guild_id")));
    }

    @Override
    public User getCreator() {
        return new UserImpl(JsonUtils.fetchEntity("/users/" + autoMod.getString("creator_id")));
    }

    @Override
    public AutoModEventType getEventType() {
        return AutoModEventType.MESSAGE_SEND;
    }

    @Override
    public AutoModTriggerType getTriggerType() {
        return EnumUtils.getEnumObject(autoMod, "trigger_type", AutoModTriggerType.class);
    }

    @Override
    public AutoModTriggerMetadata getTriggerMetadata() {
        return new AutoModTriggerMetadataImpl(autoMod.getJSONObject("trigger_metadata"));
    }

    @Override
    public List<AutoModAction> getActions() {
        return JsonUtils.getEntityList(autoMod.getJSONArray("actions"), AutoModAction::new);
    }

    @Override
    public boolean isEnabled() {
        return autoMod.getBoolean("enabled");
    }

    @Override
    public List<String> getExemptRoles() {
        return getStringList("exempt_roles");
    }

    @Override
    public List<String> getExemptChannels() {
        return getStringList("exempt_channels");
    }

    @NotNull
    private List<String> getStringList(String key) {
        List<String> list = new ArrayList<>();
        JSONArray array = autoMod.getJSONArray(key);
        for (int i = 0; i < array.length(); i++) {
            list.add(array.getString(i));
        }
        return list;
    }
}
