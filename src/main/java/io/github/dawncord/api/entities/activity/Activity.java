package io.github.dawncord.api.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.types.ActivityFlag;
import io.github.dawncord.api.types.ActivityType;
import io.github.dawncord.api.types.StatusDisplayType;
import io.github.dawncord.api.utils.LazyLoader;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Represents an implementation of the Activity interface.
 * ActivityImpl is a class implementing the Activity interface and providing methods to access activity properties.
 */
public class Activity {
    private final JsonNode activity;
    private final LazyLoader loader;
    private String name;
    private ActivityType type;
    private String url;
    private ZonedDateTime creationTimestamp;
    private ActivityTimestamp timestamps;
    private String applicationId;
    private StatusDisplayType statusDisplay;
    private String details;
    private String state;
    private String stateUrl;
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
    public Activity(JsonNode activity) {
        this.activity = activity;
        loader = new LazyLoader(activity);
    }

    public String getName() {
        name = loader.loadString(name, "name");
        return name;
    }

    public ActivityType getType() {
        type = loader.loadEnumObject(type, "type", ActivityType.class);
        return type;
    }

    public String getUrl() {
        url = loader.loadString(url, "url");
        return url;
    }

    public ZonedDateTime getCreationTimestamp() {
        creationTimestamp = loader.loadZonedDateTime(creationTimestamp, "created_at");
        return creationTimestamp;
    }

    public ActivityTimestamp getTimestamps() {
        timestamps = loader.loadIfExists(timestamps, "timestamps", ActivityTimestamp::new);
        return timestamps;
    }

    public String getApplicationId() {
        applicationId = loader.loadString(applicationId, "application_id");
        return applicationId;
    }

    public StatusDisplayType getStatusDisplay() {
        statusDisplay = loader.loadEnumObject(statusDisplay, "status_display_type", StatusDisplayType.class);
        return statusDisplay;
    }

    public String getDetails() {
        details = loader.loadString(details, "details");
        return details;
    }

    public String getState() {
        state = loader.loadString(state, "state");
        return state;
    }

    public String getStateUrl() {
        stateUrl = loader.loadString(stateUrl, "state_url");
        return stateUrl;
    }

    public ActivityEmoji getEmoji() {
        emoji = loader.loadIfExists(emoji, "emoji", ActivityEmoji::new);
        return emoji;
    }

    public ActivityParty getParty() {
        party = loader.loadIfExists(party, "party", ActivityParty::new);
        return party;
    }

    public ActivityAsset getAssets() {
        assets = loader.loadIfExists(assets, "assets",
                () -> new ActivityAsset(activity.path("assets"), activity.path("application_id").asText()));
        return assets;
    }

    public ActivitySecret getSecrets() {
        secrets = loader.loadIfExists(secrets, "secrets", ActivitySecret::new);
        return secrets;
    }

    public boolean isInstance() {
        instance = loader.loadBoolean(instance, "instance");
        return instance;
    }

    public List<ActivityFlag> getFlags() {
        flags = loader.loadEnumListFromLong(flags, "flags", ActivityFlag.class);
        return flags;
    }

    public ActivityButton getButtons() {
        buttons = loader.loadIfExists(buttons, "buttons", ActivityButton::new);
        return buttons;
    }
}
