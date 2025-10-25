package io.github.dawncord.api;

import okhttp3.MediaType;

/**
 * This class contains constant values used throughout the application.
 */
public class Constants {

    /**
     * The WebSocket Gateway URL for connecting to Discord.
     */
    public static final String DEFAULT_GATEWAY = "wss://gateway.discord.gg/?v=10&encoding=json";

    public static String GATEWAY = DEFAULT_GATEWAY;

    /**
     * The base URL for Discord's Content Delivery Network (CDN).
     */
    public static final String CDN_URL = "https://cdn.discordapp.com";

    /**
     * The base URL for Discord's API.
     */
    public static final String API_URL = "https://discord.com/api/v10";

    /**
     * The media type for JSON data.
     */
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * The application ID used for authentication.
     */
    public static String APPLICATION_ID = null;

    /**
     * The client key used for authentication.
     */
    public static String CLIENT_KEY = null;

    /**
     * The bot token used for authentication.
     */
    public static String BOT_TOKEN = null;

    /**
     * The bot ID.
     */
    public static String BOT_ID = null;

    /**
     * The session ID.
     */
    public static String SESSION_ID = null;

    /**
     * The resume URL.
     */
    public static String RESUME_URL = null;

    public static int SHARDS = 0;

    public static int LAST_SEQUENCE = 0;
}
