package io.github.dawncord.api.utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
            if (value == enumConstant.ordinal()) {
                return enumConstant;
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
    public static <T extends Enum<T>> List<T> getEnumList(JsonNode jsonArray, Class<T> enumClass) {
        List<T> enumObjects = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
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
        for (T flag : enumClass.getEnumConstants()) {
            if ((flagsFromJson & (1L << flag.ordinal())) != 0) {
                flags.add(flag);
            }
        }
        return flags;
    }
}
