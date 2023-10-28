package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.types.ActivityType;
import org.json.JSONObject;

public class ActivityImpl implements Activity {
    private final JSONObject activity;

    public ActivityImpl(JSONObject activity) {
        this.activity = activity;
    }

    @Override
    public ActivityType getType() {
        for (ActivityType type : ActivityType.values()) {
            if (type.getValue() == activity.getInt("type")) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String getPartyId() {
        return activity.getString("party_id");
    }
}
