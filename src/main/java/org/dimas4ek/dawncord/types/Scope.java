package org.dimas4ek.dawncord.types;

public enum Scope {
    ACTIVITIES_READ("activities.read", "Allows your app to fetch data from a user's 'Now Playing/Recently Played' list — not currently available for apps"),
    ACTIVITIES_WRITE("activities.write", "Allows your app to update a user's activity - not currently available for apps (NOT REQUIRED FOR GAMESDK ACTIVITY MANAGER)"),
    APPLICATIONS_BUILDS_READ("applications.builds.read", "Allows your app to read build data for a user's applications"),
    APPLICATIONS_BUILDS_UPLOAD("applications.builds.upload", "Allows your app to upload/update builds for a user's applications - requires Discord approval"),
    APPLICATIONS_COMMANDS("applications.commands", "Allows your app to add commands to a guild - included by default with the bot scope"),
    APPLICATIONS_COMMANDS_UPDATE("applications.commands.update", "Allows your app to update its commands using a Bearer token - client credentials grant only"),
    APPLICATIONS_COMMANDS_PERMISSIONS_UPDATE("applications.commands.permissions.update", "Allows your app to update permissions for its commands in a guild a user has permissions to"),
    APPLICATIONS_ENTITLEMENTS("applications.entitlements", "Allows your app to read entitlements for a user's applications"),
    APPLICATIONS_STORE_UPDATE("applications.store.update", "Allows your app to read and update store data (SKUs, store listings, achievements, etc.) for a user's applications"),
    BOT("bot", "For oauth2 bots, this puts the bot in the user's selected guild by default"),
    CONNECTIONS("connections", "Allows /users/@me/connections to return linked third-party accounts"),
    DM_CHANNELS_READ("dm_channels.read", "Allows your app to see information about the user's DMs and group DMs - requires Discord approval"),
    EMAIL("email", "Enables /users/@me to return an email"),
    GDM_JOIN("gdm.join", "Allows your app to join users to a group dm"),
    GUILDS("guilds", "Allows /users/@me/guilds to return basic information about all of a user's guilds"),
    GUILDS_JOIN("guilds.join", "Allows /guilds/{guild.id}/members/{user.id} to be used for joining users to a guild"),
    GUILDS_MEMBERS_READ("guilds.members.read", "Allows /users/@me/guilds/{guild.id}/member to return a user's member information in a guild"),
    IDENTIFY("identify", "Allows /users/@me without email"),
    MESSAGES_READ("messages.read", "For local rpc server api access, this allows you to read messages from all client channels (otherwise restricted to channels/guilds your app creates)"),
    RELATIONSHIPS_READ("relationships.read", "Allows your app to know a user's friends and implicit relationships - requires Discord approval"),
    ROLE_CONNECTIONS_WRITE("role_connections.write", "Allows your app to update a user's connection and metadata for the app"),
    RPC("rpc", "For local rpc server access, this allows you to control a user's local Discord client - requires Discord approval"),
    RPC_ACTIVITIES_WRITE("rpc.activities.write", "For local rpc server access, this allows you to update a user's activity - requires Discord approval"),
    RPC_NOTIFICATIONS_READ("rpc.notifications.read", "For local rpc server access, this allows you to receive notifications pushed out to the user - requires Discord approval"),
    RPC_VOICE_READ("rpc.voice.read", "For local rpc server access, this allows you to read a user's voice settings and listen for voice events - requires Discord approval"),
    RPC_VOICE_WRITE("rpc.voice.write", "For local rpc server access, this allows you to update a user's voice settings - requires Discord approval"),
    VOICE("voice", "Allows your app to connect to voice on the user's behalf and see all the voice members - requires Discord approval"),
    WEBHOOK_INCOMING("webhook.incoming", "This generates a webhook that is returned in the OAuth token response for authorization code grants");

    private final String value;
    private final String description;

    Scope(String value, String description) {
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
