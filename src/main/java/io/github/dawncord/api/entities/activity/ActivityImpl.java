package io.github.dawncord.api.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.types.ActivityFlag;
import io.github.dawncord.api.types.ActivityType;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.TimeUtils;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Represents an implementation of the Activity interface.
 * ActivityImpl is a class implementing the Activity interface and providing methods to access activity properties.
 */
public class ActivityImpl implements Activity {
    private final JsonNode activity;
    private String name;
    private ActivityType type;
    private String url;
    private ZonedDateTime creationTimestamp;
    private ActivityTimestamp timestamps;
    private String applicationId;
    private String details;
    private String state;
    private ActivityEmoji emoji;
    private ActivityParty party;
    private ActivityAsset assets;
    private ActivitySecret secrets;
    private Boolean instance;
    private List<ActivityFlag> flags;
    private ActivityButton buttons;

    /**
     * Constructs an ActivityImpl object with the provided JSON node containing activity information.
     *
     * @param activity The JSON node containing activity information.
     */
    public ActivityImpl(JsonNode activity) {
        this.activity = activity;
    }

    @Override
    public String getName() {
        if (name == null) {
            name = activity.path("name").asText();
        }
        return name;
    }

    @Override
    public ActivityType getType() {
        if (type == null) {
            type = EnumUtils.getEnumObject(activity, "type", ActivityType.class);
        }
        return type;
    }

    @Override
    public String getUrl() {
        if (url == null) {
            url = activity.has("url") ? activity.path("url").asText() : null;
        }
        return url;
    }

    @Override
    public ZonedDateTime getCreationTimestamp() {
        if (creationTimestamp == null) {
            creationTimestamp = TimeUtils.getZonedDateTime(activity, "created_at");
        }
        return creationTimestamp;
    }

    @Override
    public ActivityTimestamp getTimestamps() {
        if (timestamps == null) {
            if (activity.has("timestamps")) {
                timestamps = new ActivityTimestamp(activity.path("timestamps"));
            }
        }
        return timestamps;
    }

    @Override
    public String getApplicationId() {
        if (applicationId == null) {
            applicationId = activity.has("application_id") ? activity.path("application_id").asText() : null;
        }
        return applicationId;
    }

    @Override
    public String getDetails() {
        if (details == null) {
            details = activity.has("details") ? activity.path("details").asText() : null;
        }
        return details;
    }

    @Override
    public String getState() {
        if (state == null) {
            state = activity.has("state") ? activity.path("state").asText() : null;
        }
        return state;
    }

    @Override
    public ActivityEmoji getEmoji() {
        if (emoji == null) {
            if (activity.has("emoji")) {
                emoji = new ActivityEmoji(activity.path("emoji"));
            }
        }
        return emoji;
    }

    @Override
    public ActivityParty getParty() {
        if (party == null) {
            if (activity.has("party")) {
                party = new ActivityParty(activity.path("party"));
            }
        }
        return party;
    }

    @Override
    public ActivityAsset getAssets() {
        if (assets == null) {
            if (activity.has("assets")) {
                assets = new ActivityAsset(activity.path("assets"), activity.path("application_id").asText());
            }
        }
        return assets;
    }

    @Override
    public ActivitySecret getSecrets() {
        if (assets == null) {
            if (activity.has("secrets")) {
                secrets = new ActivitySecret(activity.path("secrets"));
            }
        }
        return secrets;
    }

    @Override
    public boolean isInstance() {
        if (instance == null) {
            if (activity.has("instance")) {
                instance = activity.path("instance").asBoolean();
            }
        }
        return instance;
    }

    @Override
    public List<ActivityFlag> getFlags() {
        if (flags == null) {
            if (activity.has("flags")) {
                flags = EnumUtils.getEnumListFromLong(activity, "flags", ActivityFlag.class);
            }
        }
        return flags;
    }

    @Override
    public ActivityButton getButtons() {
        if (buttons == null) {
            if (activity.has("buttons")) {
                buttons = new ActivityButton(activity.path("buttons"));
            }
        }
        return buttons;
    }
}
