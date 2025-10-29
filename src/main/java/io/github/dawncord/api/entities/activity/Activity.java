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
        name = loader.loadStringIfNull(name, "name");
        return name;
    }

    public ActivityType getType() {
        type = loader.loadEnumIfNull(type, "type", ActivityType.class);
        return type;
    }

    public String getUrl() {
        url = loader.loadStringIfExistsAndNull(url, "url");
        return url;
    }

    public ZonedDateTime getCreationTimestamp() {
        creationTimestamp = loader.loadZonedDateTimeIfNull(creationTimestamp, "created_at");
        return creationTimestamp;
    }

    public ActivityTimestamp getTimestamps() {
        timestamps = loader.loadIfExistsAndNull(timestamps, "timestamps", ActivityTimestamp::new);
        return timestamps;
    }

    public String getApplicationId() {
        applicationId = loader.loadStringIfExistsAndNull(applicationId, "application_id");
        return applicationId;
    }

    public StatusDisplayType getStatusDisplay() {
        statusDisplay = loader.loadEnumIfNull(statusDisplay, "status_display_type", StatusDisplayType.class);
        return statusDisplay;
    }

    public String getDetails() {
        details = loader.loadStringIfExistsAndNull(details, "details");
        return details;
    }

    public String getState() {
        state = loader.loadStringIfExistsAndNull(state, "state");
        return state;
    }
    
    public String getStateUrl() {
        stateUrl = loader.loadStringIfExistsAndNull(stateUrl, "state_url");
        return stateUrl;
    }

    public ActivityEmoji getEmoji() {
        emoji = loader.loadIfExistsAndNull(emoji, "emoji", ActivityEmoji::new);
        return emoji;
    }

    public ActivityParty getParty() {
        party = loader.loadIfExistsAndNull(party, "party", ActivityParty::new);
        return party;
    }

    public ActivityAsset getAssets() {
        assets = loader.loadIfExistsAndNull(assets, "assets",
                () -> new ActivityAsset(activity.path("assets"), activity.path("application_id").asText()));
        return assets;
    }

    public ActivitySecret getSecrets() {
        secrets = loader.loadIfExistsAndNull(secrets, "secrets", ActivitySecret::new);
        return secrets;
    }

    public boolean isInstance() {
        instance = loader.loadBooleanIfExistsNull(instance, "instance");
        return instance;
    }

    public List<ActivityFlag> getFlags() {
        flags = loader.loadEnumListFromLongIfNull(flags, "flags", ActivityFlag.class);
        return flags;
    }

    public ActivityButton getButtons() {
        buttons = loader.loadIfExistsAndNull(buttons, "buttons", ActivityButton::new);
        return buttons;
    }
}
