package org.dimas4ek.wrapper.entities.application;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.types.ActivityType;
import org.dimas4ek.wrapper.utils.EnumUtils;

public class ActivityImpl implements Activity {
    private final JsonNode activity;
    private ActivityType type;
    private String partyId;

    public ActivityImpl(JsonNode activity) {
        this.activity = activity;
    }

    @Override
    public ActivityType getType() {
        if (type == null) {
            type = EnumUtils.getEnumObject(activity, "type", ActivityType.class);
        }
        return type;
    }

    @Override
    public String getPartyId() {
        if (partyId == null) {
            partyId = activity.get("party_id").asText();
        }
        return partyId;
    }
}
