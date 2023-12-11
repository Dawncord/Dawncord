package org.dimas4ek.wrapper.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.dimas4ek.wrapper.ApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class JsonUtils {
    public static JsonNode fetchEntity(String url) {
        return ApiClient.getJson(url);
    }

    public static <T> List<T> getEntityList(JsonNode array, Function<JsonNode, T> constructor) {
        List<T> list = new ArrayList<>();

        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                list.add(constructor.apply(array.get(i)));
            }
        }

        return list;
    }

    public static JsonNode fetchEntityParams(String url, Map<String, String> params) {
        return ApiClient.getJsonParams(url, params);
    }

    public static ArrayNode fetchArrayParams(String url, Map<String, String> params) {
        return ApiClient.getJsonArrayParams(url, params);
    }

    public static JsonNode fetchArray(String url) {
        return ApiClient.getJsonArray(url);
    }

    public static List<String> fetchStringList(JsonNode json, String object) {
        if (json.has(object)) {
            List<String> list = new ArrayList<>();
            for (JsonNode node : json.get(object)) {
                list.add(node.asText());
            }
            return list;
        }
        return Collections.emptyList();
    }
}
