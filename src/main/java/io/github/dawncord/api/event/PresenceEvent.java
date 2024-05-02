package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.ClientStatus;
import io.github.dawncord.api.entities.activity.Activity;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.types.ActivityType;
import io.github.dawncord.api.types.OnlineStatus;

import java.util.List;

/**
 * Represents an event related to a member's presence update in a guild.
 */
public class PresenceEvent implements Event {
    private final Guild guild;
    private final GuildMember member;
    private final OnlineStatus status;
    private final ClientStatus clientStatus;
    private final List<Activity> activities;

    /**
     * Constructs a PresenceEvent with the specified guild, member, status, client status, and activities.
     *
     * @param guild        The guild where the presence update occurred.
     * @param member       The member whose presence was updated.
     * @param status       The online status of the member.
     * @param clientStatus The client status of the member.
     * @param activities   The list of activities associated with the member's presence.
     */
    public PresenceEvent(Guild guild, GuildMember member, OnlineStatus status, ClientStatus clientStatus, List<Activity> activities) {
        this.guild = guild;
        this.member = member;
        this.status = status;
        this.clientStatus = clientStatus;
        this.activities = activities;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    /**
     * Retrieves the member whose presence was updated.
     *
     * @return The guild member.
     */
    public GuildMember getMember() {
        return member;
    }

    /**
     * Retrieves the online status of the member.
     *
     * @return The online status.
     */
    public OnlineStatus getStatus() {
        return status;
    }

    /**
     * Retrieves the client status of the member.
     *
     * @return The client status.
     */
    public ClientStatus getClientStatus() {
        return clientStatus;
    }

    /**
     * Retrieves the list of activities associated with the member's presence.
     *
     * @return The list of activities.
     */
    public List<Activity> getActivities() {
        return activities;
    }

    /**
     * Retrieves an activity by its name.
     *
     * @param name The name of the activity to retrieve.
     * @return The activity with the specified name, or null if not found.
     */
    public Activity getActivityByName(String name) {
        for (Activity activity : activities) {
            if (activity.getName().equals(name)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * Retrieves an activity by its type.
     *
     * @param type The type of the activity to retrieve.
     * @return The activity with the specified type, or null if not found.
     */
    public Activity getActivityByType(ActivityType type) {
        for (Activity activity : activities) {
            if (activity.getType().equals(type)) {
                return activity;
            }
        }
        return null;
    }
}
