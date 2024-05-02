package io.github.dawncord.api.types;

/**
 * Represents different OAuth2 Scopes.
 */
public enum Scope {
    /**
     * Allows your app to fetch data from a user's 'Now Playing/Recently Played' list.
     */
    ACTIVITIES_READ("activities.read"),

    /**
     * Allows your app to update a user's activity.
     */
    ACTIVITIES_WRITE("activities.write"),

    /**
     * Allows your app to read build data for a user's applications.
     */
    APPLICATIONS_BUILDS_READ("applications.builds.read"),

    /**
     * Allows your app to upload/update builds for a user's applications.
     */
    APPLICATIONS_BUILDS_UPLOAD("applications.builds.upload"),

    /**
     * Allows your app to add commands to a guild.
     */
    APPLICATIONS_COMMANDS("applications.commands"),

    /**
     * Allows your app to update its commands using a Bearer token.
     */
    APPLICATIONS_COMMANDS_UPDATE("applications.commands.update"),

    /**
     * Allows your app to update permissions for its commands in a guild a user has permissions to.
     */
    APPLICATIONS_COMMANDS_PERMISSIONS_UPDATE("applications.commands.permissions.update"),

    /**
     * Allows your app to read entitlements for a user's applications.
     */
    APPLICATIONS_ENTITLEMENTS("applications.entitlements"),

    /**
     * Allows your app to read and update store data for a user's applications.
     */
    APPLICATIONS_STORE_UPDATE("applications.store.update"),

    /**
     * For oauth2 bots, this puts the bot in the user's selected guild by default.
     */
    BOT("bot"),

    /**
     * Allows /users/@me/connections to return linked third-party accounts.
     */
    CONNECTIONS("connections"),

    /**
     * Allows your app to see information about the user's DMs and group DMs.
     */
    DM_CHANNELS_READ("dm_channels.read"),

    /**
     * Enables /users/@me to return an email.
     */
    EMAIL("email"),

    /**
     * Allows your app to join users to a group dm.
     */
    GDM_JOIN("gdm.join"),

    /**
     * Allows /users/@me/guilds to return basic information about all of a user's guilds.
     */
    GUILDS("guilds"),

    /**
     * Allows /guilds/{guild.id}/members/{user.id} to be used for joining users to a guild.
     */
    GUILDS_JOIN("guilds.join"),

    /**
     * Allows /users/@me/guilds/{guild.id}/member to return a user's member information in a guild.
     */
    GUILDS_MEMBERS_READ("guilds.members.read"),

    /**
     * Allows /users/@me without email.
     */
    IDENTIFY("identify"),

    /**
     * For local rpc server api access, this allows you to read messages from all client channels.
     */
    MESSAGES_READ("messages.read"),

    /**
     * Allows your app to know a user's friends and implicit relationships.
     */
    RELATIONSHIPS_READ("relationships.read"),

    /**
     * Allows your app to update a user's connection and metadata for the app.
     */
    ROLE_CONNECTIONS_WRITE("role_connections.write"),

    /**
     * For local rpc server access, this allows you to control a user's local Discord client.
     */
    RPC("rpc"),

    /**
     * For local rpc server access, this allows you to update a user's activity.
     */
    RPC_ACTIVITIES_WRITE("rpc.activities.write"),

    /**
     * For local rpc server access, this allows you to receive notifications pushed out to the user.
     */
    RPC_NOTIFICATIONS_READ("rpc.notifications.read"),

    /**
     * For local rpc server access, this allows you to read a user's voice settings and listen for voice events.
     */
    RPC_VOICE_READ("rpc.voice.read"),

    /**
     * For local rpc server access, this allows you to update a user's voice settings.
     */
    RPC_VOICE_WRITE("rpc.voice.write"),

    /**
     * Allows your app to connect to voice on the user's behalf and see all the voice members.
     */
    VOICE("voice"),

    /**
     * This generates a webhook that is returned in the OAuth token response for authorization code grants.
     */
    WEBHOOK_INCOMING("webhook.incoming");

    private final String value;

    Scope(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the scope.
     *
     * @return The value of the scope.
     */
    public String getValue() {
        return value;
    }
}
