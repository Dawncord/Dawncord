package org.dimas4ek.dawncord.event;

import org.dimas4ek.dawncord.entities.ClientStatus;
import org.dimas4ek.dawncord.entities.activity.Activity;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.entities.guild.GuildMember;
import org.dimas4ek.dawncord.types.ActivityType;
import org.dimas4ek.dawncord.types.OnlineStatus;

import java.util.List;

public class PresenceEvent implements Event {
    private final Guild guild;
    private final GuildMember member;
    private final OnlineStatus status;
    private final ClientStatus clientStatus;
    private final List<Activity> activities;

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

    public GuildMember getMember() {
        return member;
    }

    public OnlineStatus getStatus() {
        return status;
    }

    public ClientStatus getClientStatus() {
        return clientStatus;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public Activity getActivityByName(String name) {
        for (Activity activity : activities) {
            if (activity.getName().equals(name)) {
                return activity;
            }
        }
        return null;
    }

    public Activity getActivityByType(ActivityType type) {
        for (Activity activity : activities) {
            if (activity.getType().equals(type)) {
                return activity;
            }
        }
        return null;
    }
}
