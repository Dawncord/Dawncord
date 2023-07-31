package org.dimas4ek.utils;

public class Endpoints {
    public static class Guilds {
        public static final String GUILD = "/guilds/%s";
        public static final String GUILD_CHANNELS = "/guilds/%s/channels";
        public static final String GUILD_MEMBERS = "/guilds/%s/members";
        public static final String GUILD_MEMBER = "/guilds/%s/members/%s";
        public static final String GUILD_ROLES = "/guilds/%s/roles";
    }
    
    public static String getMethod(String endpoint, Object... args) {
        return String.format(Constants.API_URL + endpoint, args);
    }
}
