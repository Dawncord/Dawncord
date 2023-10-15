package org.dimas4ek.wrapper.utils;

import org.dimas4ek.wrapper.ApiClient;
import org.json.JSONObject;

public class JsonUtils {
    public static JSONObject fetchEntity(String url) {
        return ApiClient.getJsonObject(url);
    }
}
