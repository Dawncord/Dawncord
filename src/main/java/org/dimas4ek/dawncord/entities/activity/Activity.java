package org.dimas4ek.dawncord.entities.activity;

import org.dimas4ek.dawncord.types.ActivityFlag;
import org.dimas4ek.dawncord.types.ActivityType;

import java.time.ZonedDateTime;
import java.util.List;

public interface Activity {
    String getName();

    ActivityType getType();

    String getUrl();

    ZonedDateTime getCreationTimestamp();

    ActivityTimestamp getTimestamps();

    String getApplicationId();

    String getDetails();

    String getState();

    ActivityEmoji getEmoji();

    ActivityParty getParty();

    ActivityAsset getAssets();

    ActivitySecret getSecrets();

    boolean isInstance();

    List<ActivityFlag> getFlags();

    ActivityButton getButtons();
}
