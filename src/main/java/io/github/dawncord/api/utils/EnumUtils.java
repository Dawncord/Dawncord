package io.github.dawncord.api.utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Utility class for working with enums.
 */
public class EnumUtils {

    /**
     * Retrieves an enum object from its integer value.
     *
     * @param value     The integer value.
     * @param enumClass The enum class.
     * @param <T>       The enum type.
     * @return The enum object corresponding to the given integer value, or null if not found.
     */
    public static <T extends Enum<T>> T getEnumObjectFromInt(int value, Class<T> enumClass) {
        for (T enumConstant : enumClass.getEnumConstants()) {
            try {
                Method m = enumClass.getMethod("getValue");
                if (value == (int) m.invoke(enumConstant)) {
                    return enumConstant;
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                if (value == enumConstant.ordinal()) {
                    return enumConstant;
                }
            }
        }
        return null;
    }

    /**
     * Retrieves an enum object from a JSON node by its integer value.
     *
     * @param json      The JSON node containing the value.
     * @param key       The key corresponding to the value in the JSON node.
     * @param enumClass The enum class.
     * @param <T>       The enum type.
     * @return The enum object corresponding to the given integer value, or null if not found.
     */
    public static <T extends Enum<T>> T getEnumObject(JsonNode json, String key, Class<T> enumClass) {
        int intValue = json.get(key).asInt(-1);
        for (T obj : enumClass.getEnumConstants()) {
            try {
                Method m = enumClass.getMethod("getValue");
                if (intValue >= 0 && intValue == (int) m.invoke(obj)) {
                    return obj;
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

    /**
     * Retrieves a list of enum objects from a JSON array by their integer values.
     *
     * @param jsonArray The JSON array containing the integer values.
     * @param enumClass The enum class.
     * @param <T>       The enum type.
     * @return The list of enum objects corresponding to the given integer values.
     */
    public static <T extends Enum<T>> List<T> getEnumList(JsonNode jsonArray, Class<T> enumClass, String type) {
        List<T> enumObjects = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            switch (type) {
                case "int" -> {
                    int intValue = jsonArray.get(i).asInt(-1);
                    for (T obj : enumClass.getEnumConstants()) {
                        try {
                            Method m = enumClass.getMethod("getValue");
                            if (intValue >= 0 && intValue == (int) m.invoke(obj)) {
                                enumObjects.add(obj);
                            }
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                case "str" -> {
                    String strValue = jsonArray.get(i).asText();
                    for (T obj : enumClass.getEnumConstants()) {
                        try {
                            Method m = enumClass.getMethod("getValue");
                            if (strValue != null && !strValue.isEmpty() && strValue.equals(m.invoke(obj))) {
                                enumObjects.add(obj);
                            }
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }

        }
        return enumObjects;
    }

    /**
     * Retrieves a list of enum objects from a JSON node by their long values.
     *
     * @param json      The JSON node containing the long value.
     * @param key       The key corresponding to the value in the JSON node.
     * @param enumClass The enum class.
     * @param <T>       The enum type.
     * @return The list of enum objects corresponding to the given long value.
     */
    public static <T extends Enum<T>> List<T> getEnumListFromLong(JsonNode json, String key, Class<T> enumClass) {
        long flagsFromJson = json.get(key).asLong(0);
        List<T> flags = new ArrayList<>();
        try {
            Method getValue = enumClass.getMethod("getValue");
            for (T flag : enumClass.getEnumConstants()) {
                long value = ((Number) getValue.invoke(flag)).longValue();
                if ((flagsFromJson & value) != 0) {
                    flags.add(flag);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            for (T flag : enumClass.getEnumConstants()) {
                if ((flagsFromJson & (1L << flag.ordinal())) != 0) {
                    flags.add(flag);
                }
            }
        }
        return flags;
    }

    /**
     * Maps a JSON array of strings to a list of name-only enum constants.
     * Values with no matching constant are skipped rather than throwing, so unknown
     * values newly introduced by Discord do not break deserialization.
     *
     * @param json      The JSON array node of enum-name strings.
     * @param enumClass The name-only enum class to resolve against.
     * @param <T>       The enum type.
     * @return The resolved constants; an empty list if {@code json} is null or not an array.
     */
    public static <T extends Enum<T>> List<T> getEnumValues(JsonNode json, Class<T> enumClass) {
        if (json == null || !json.isArray()) {
            return Collections.emptyList();
        }

        return StreamSupport.stream(json.spliterator(), false)
                .filter(JsonNode::isTextual)
                .map(JsonNode::asText)
                .map(value -> {
                    try {
                        return Enum.valueOf(enumClass, value);
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
