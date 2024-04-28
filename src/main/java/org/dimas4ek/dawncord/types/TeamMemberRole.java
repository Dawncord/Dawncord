package org.dimas4ek.dawncord.types;

public enum TeamMemberRole {
    OWNER("owner", "Owners are the most permissiable role, and can take destructive, irreversible actions like deleting team-owned apps or the team itself. Teams are limited to 1 owner."),
    ADMIN("admin", "Admins have similar access as owners, except they cannot take destructive actions on the team or team-owned apps."),
    DEVELOPER("developer", "Developers can access information about team-owned apps, like the client secret or public key. They can also take limited actions on team-owned apps, like configuring interaction endpoints or resetting the bot token. Members with the Developer role cannot manage the team or its members, or take destructive actions on team-owned apps."),
    READ_ONLY("read_only", "Read-only members can access information about a team and any team-owned apps. Some examples include getting the IDs of applications and exporting payout records.");

    private final String value;
    private final String description;

    TeamMemberRole(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
