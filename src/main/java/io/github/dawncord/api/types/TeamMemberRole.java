package io.github.dawncord.api.types;

/**
 * Represents roles that a team member can have.
 */
public enum TeamMemberRole {
    // Roles

    /**
     * The owner role.
     * <p>
     * Owners are the most permissive role, and can take destructive,
     * irreversible actions like deleting team-owned apps or the team itself.
     * <p>
     * Teams are limited to 1 owner.
     */
    OWNER("owner"),

    /**
     * The admin role.
     * <p>
     * Admins have similar access as owners, except they cannot take destructive actions on the team or team-owned apps.
     */
    ADMIN("admin"),

    /**
     * The developer role.
     * <p>
     * Developers can access information about team-owned apps, like the client secret or public key.
     * <p>
     * They can also take limited actions on team-owned apps,
     * like configuring interaction endpoints or resetting the bot token.
     * <p>
     * Members with the Developer role cannot manage the team or its members,
     * or take destructive actions on team-owned apps.
     */
    DEVELOPER("developer"),

    /**
     * The read-only role.
     * <p>
     * Read-only members can access information about a team and any team-owned apps.
     * <p>
     * Some examples include getting the IDs of applications and exporting payout records.
     */
    READ_ONLY("read_only");

    private final String value;

    TeamMemberRole(String value) {
        this.value = value;
    }

    /**
     * Gets the value representing the role.
     *
     * @return The value representing the role.
     */
    public String getValue() {
        return value;
    }
}
