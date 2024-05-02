package io.github.dawncord.api.utils;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.ApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for handling JSON-related operations.
 */
public class JsonUtils {

    /**
     * Fetches JSON data from the specified URL.
     *
     * @param url The URL from which to fetch JSON data.
     * @return The JSON data as a JsonNode object.
     */
    public static JsonNode fetch(String url) {
        return ApiClient.getJson(url);
    }

    /**
     * Fetches JSON data from the specified URL with additional parameters.
     *
     * @param url    The URL from which to fetch JSON data.
     * @param params The parameters to include in the request.
     * @return The JSON data as a JsonNode object.
     */
    public static JsonNode fetchParams(String url, Map<String, String> params) {
        return ApiClient.getJsonParams(url, params);
    }

    /**
     * Extracts a list of entities from a JSON array using a constructor function.
     *
     * @param array       The JSON array containing entity data.
     * @param constructor A function to construct entities from JSON nodes.
     * @param <T>         The type of the entities.
     * @return A list of constructed entities.
     */
    public static <T> List<T> getEntityList(JsonNode array, Function<JsonNode, T> constructor) {
        List<T> list = new ArrayList<>();

        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                list.add(constructor.apply(array.get(i)));
            }
        }

        return list;
    }

    /**
     * Extracts a list of strings from a JSON object.
     *
     * @param json   The JSON object containing string data.
     * @param object The key of the JSON object containing the string array.
     * @return A list of strings extracted from the JSON object.
     */
    public static List<String> getStringList(JsonNode json, String object) {
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
