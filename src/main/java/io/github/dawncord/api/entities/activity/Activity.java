package io.github.dawncord.api.entities.activity;

import io.github.dawncord.api.types.ActivityFlag;
import io.github.dawncord.api.types.ActivityType;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Represents an activity.
 * Activity is an interface defining methods to access various properties of an activity.
 */
public interface Activity {

    /**
     * Retrieves the name of the activity.
     *
     * @return The name of the activity.
     */
    String getName();

    /**
     * Retrieves the type of the activity.
     *
     * @return The type of the activity.
     */
    ActivityType getType();

    /**
     * Retrieves the URL associated with the activity.
     *
     * @return The URL of the activity.
     */
    String getUrl();

    /**
     * Retrieves the creation timestamp of the activity.
     *
     * @return The creation timestamp of the activity.
     */
    ZonedDateTime getCreationTimestamp();

    /**
     * Retrieves the timestamps of the activity.
     *
     * @return The timestamps of the activity.
     */
    ActivityTimestamp getTimestamps();

    /**
     * Retrieves the application ID associated with the activity.
     *
     * @return The application ID of the activity.
     */
    String getApplicationId();

    /**
     * Retrieves the details of the activity.
     *
     * @return The details of the activity.
     */
    String getDetails();

    /**
     * Retrieves the state of the activity.
     *
     * @return The state of the activity.
     */
    String getState();

    /**
     * Retrieves the emoji associated with the activity.
     *
     * @return The emoji of the activity.
     */
    ActivityEmoji getEmoji();

    /**
     * Retrieves the party information associated with the activity.
     *
     * @return The party information of the activity.
     */
    ActivityParty getParty();

    /**
     * Retrieves the assets associated with the activity.
     *
     * @return The assets of the activity.
     */
    ActivityAsset getAssets();

    /**
     * Retrieves the secrets associated with the activity.
     *
     * @return The secrets of the activity.
     */
    ActivitySecret getSecrets();

    /**
     * Checks if the activity is an instance.
     *
     * @return True if the activity is an instance, false otherwise.
     */
    boolean isInstance();

    /**
     * Retrieves the flags associated with the activity.
     *
     * @return The flags of the activity.
     */
    List<ActivityFlag> getFlags();

    /**
     * Retrieves the button associated with the activity.
     *
     * @return The button of the activity.
     */
    ActivityButton getButtons();
}
