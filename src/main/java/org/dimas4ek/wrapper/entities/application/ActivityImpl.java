package org.dimas4ek.wrapper.entities.application;

import org.dimas4ek.wrapper.types.ActivityType;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.json.JSONObject;

public class ActivityImpl implements Activity {
    private final JSONObject activity;

    public ActivityImpl(JSONObject activity) {
        this.activity = activity;
    }

    @Override
    public ActivityType getType() {
        return EnumUtils.getEnumObject(activity, "type", ActivityType.class);
    }

    @Override
    public String getPartyId() {
        return activity.getString("party_id");
    }
}
