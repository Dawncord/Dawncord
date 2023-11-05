package org.dimas4ek.wrapper.utils;

import org.dimas4ek.wrapper.ApiClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class JsonUtils {
    public static JSONObject fetchEntity(String url) {
        return ApiClient.getJsonObject(url);
    }

    public static JSONObject fetchEntityParams(String url, Map<String, String> params) {
        return ApiClient.getJsonObjectParams(url, params);
    }

    public static JSONArray fetchArray(String url) {
        return ApiClient.getJsonArray(url);
    }

    public static JSONArray fetchArrayParams(String url, Map<String, String> params) {
        return ApiClient.getJsonArrayParams(url, params);
    }

    public static <T> List<T> getEntityList(JSONArray jsonArray, Function<JSONObject, T> constructor) {
        try {
            List<T> list = new ArrayList<>();

            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(constructor.apply(jsonArray.getJSONObject(i)));
                }
            }

            return list;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
