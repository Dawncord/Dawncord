package org.dimas4ek.wrapper.entities.guild.automod;

import org.dimas4ek.wrapper.types.AutoModActionType;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.json.JSONObject;

public class AutoModAction {
    private final JSONObject action;

    public AutoModAction(JSONObject action) {
        this.action = action;
    }

    public AutoModActionType getType() {
        return EnumUtils.getEnumObject(action, "type", AutoModActionType.class);
    }

    public ActionMetadata getMetadata() {
        JSONObject metadata = action.optJSONObject("metadata");
        return metadata != null
                ? new ActionMetadata(metadata)
                : null;
    }
}
