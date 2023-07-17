package org.dimas4ek.utils;

import okhttp3.MediaType;

public class Constants {
    public static final String API_URL = "https://discord.com/api/v10";
    public static final String GATEWAY = "wss://gateway.discord.gg/?v=10&encoding=json";
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static String CLIENT_KEY = null;
    public static String APPLICATION_ID = null;
    public static String BOT_TOKEN = null;
}