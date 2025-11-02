package io.github.dawncord.api.utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utility class for lazy loading of values from JSON data.
 * Provides methods to load values from JSON nodes only when they are null.
 */
public class LazyLoader {
    private final JsonNode jsonNode;

    /**
     * Constructs a LazyLoader with the specified JSON node.
     *
     * @param jsonNode the JSON node from which to extract data
     */
    public LazyLoader(JsonNode jsonNode) {
        this.jsonNode = jsonNode;
    }

    /**
     * Loads a string value from JSON if the current value is null.
     *
     * @param currentValue the current string value (may be null)
     * @param fieldName    the name of the field in the JSON node
     * @return the current value if not null, otherwise the string value from JSON
     */
    public String loadString(String currentValue, String fieldName) {
        if (currentValue == null) {
            return jsonNode.path(fieldName).asText();
        }
        return currentValue;
    }

    public String loadStringIfExists(String currentValue, String fieldName) {
        if (currentValue == null) {
            if (jsonNode.has(fieldName)) {
                return jsonNode.path(fieldName).asText();
            }
        }
        return currentValue;
    }

    /**
     * Loads an integer value from JSON if the current value is null.
     *
     * @param currentValue the current integer value (may be null)
     * @param fieldName    the name of the field in the JSON node
     * @return the current value if not null, otherwise the integer value from JSON
     */
    public Integer loadInt(Integer currentValue, String fieldName) {
        if (currentValue == null) {
            return jsonNode.path(fieldName).asInt(0);
        }
        return currentValue;
    }

    public Integer loadIntIfExists(Integer currentValue, String fieldName) {
        if (currentValue == null) {
            if (jsonNode.has(fieldName)) {
                return jsonNode.path(fieldName).asInt(0);
            }
        }
        return currentValue;
    }

    /**
     * Loads an integer value from JSON array if the current value is null.
     *
     * @param currentValue the current integer value (may be null)
     * @param fieldName    the name of the array field in the JSON node
     * @param arrayIndex   the index in the array
     * @return the current value if not null, otherwise the integer value from JSON array
     * @throws IndexOutOfBoundsException if the array doesn't have the specified index
     */
    public Integer loadIntFromArray(Integer currentValue, String fieldName, int arrayIndex) {
        if (currentValue == null) {
            JsonNode arrayNode = jsonNode.path(fieldName);
            if (arrayNode.isArray() && arrayIndex < arrayNode.size()) {
                return arrayNode.get(arrayIndex).asInt();
            }
        }
        return currentValue;
    }

    /**
     * Loads a long value from JSON if the current value is null.
     *
     * @param currentValue the current long value (may be null)
     * @param fieldName    the name of the field in the JSON node
     * @return the current value if not null, otherwise the long value from JSON
     */
    public Long loadLong(Long currentValue, String fieldName) {
        if (currentValue == null) {
            return jsonNode.path(fieldName).asLong(0L);
        }
        return currentValue;
    }

    public Long loadLongIfExists(Long currentValue, String fieldName) {
        if (currentValue == null) {
            if (jsonNode.has(fieldName)) {
                return jsonNode.path(fieldName).asLong(0L);
            }
        }
        return currentValue;
    }

    /**
     * Loads a boolean value from JSON if the current value is null.
     *
     * @param currentValue the current boolean value (may be null)
     * @param fieldName    the name of the field in the JSON node
     * @return the current value if not null, otherwise the boolean value from JSON
     */
    public Boolean loadBoolean(Boolean currentValue, String fieldName) {
        if (currentValue == null) {
            return jsonNode.path(fieldName).asBoolean(false);
        }
        return currentValue;
    }

    public Boolean loadBooleanIfExists(Boolean currentValue, String fieldName) {
        if (currentValue == null) {
            if (jsonNode.has(fieldName)) {
                return jsonNode.path(fieldName).asBoolean(false);
            }
        }
        return currentValue;
    }

    /**
     * Loads an enum value from JSON if the current value is null.
     * Uses the EnumUtils.getEnumObject method for conversion.
     *
     * @param <T>          the enum type
     * @param currentValue the current enum value (may be null)
     * @param fieldName    the name of the field in the JSON node
     * @param enumClass    the class of the enum
     * @return the current value if not null, otherwise the enum value from JSON
     */
    public <T extends Enum<T>> T loadEnumObject(T currentValue, String fieldName, Class<T> enumClass) {
        if (currentValue == null && jsonNode.has(fieldName)) {
            return EnumUtils.getEnumObject(jsonNode, fieldName, enumClass);
        }
        return currentValue;
    }

    public <T extends Enum<T>> List<T> loadEnumList(List<T> currentValue, String fieldName, Class<T> enumClass, String type) {
        if (currentValue == null && jsonNode.has(fieldName)) {
            return EnumUtils.getEnumList(jsonNode.get(fieldName), enumClass, type);
        }
        return currentValue;
    }

    /**
     * Loads a list of enum values from JSON if the current value is null and the field exists.
     * Uses the EnumUtils.getEnumListFromLong method for conversion.
     *
     * @param <T>          the enum type
     * @param currentValue the current list of enum values (may be null)
     * @param fieldName    the name of the field in the JSON node
     * @param enumClass    the class of the enum
     * @return the current value if not null, otherwise the list of enum values from JSON if field exists, otherwise null
     */
    public <T extends Enum<T>> List<T> loadEnumListFromLong(List<T> currentValue, String fieldName, Class<T> enumClass) {
        if (currentValue == null && jsonNode.has(fieldName)) {
            return EnumUtils.getEnumListFromLong(jsonNode, fieldName, enumClass);
        }
        return currentValue;
    }

    /**
     * Loads a ZonedDateTime value using TimeUtils if the current value is null.
     *
     * @param currentValue the current ZonedDateTime value (may be null)
     * @param fieldName    the name of the field in the JSON node
     * @return the current value if not null, otherwise the ZonedDateTime value from JSON
     */
    public ZonedDateTime loadZonedDateTime(ZonedDateTime currentValue, String fieldName) {
        if (currentValue == null) {
            return TimeUtils.getZonedDateTime(jsonNode, fieldName);
        }
        return currentValue;
    }

    /**
     * Loads a value from JSON using a custom mapper function if the current value is null.
     *
     * @param <T>          the type of the value to load
     * @param currentValue the current value (may be null)
     * @param fieldName    the name of the field in the JSON node
     * @param mapper       function that maps JSON node to the desired type
     * @return the current value if not null, otherwise the mapped value from JSON
     */
    public <T> T loadMapped(T currentValue, String fieldName, Function<JsonNode, T> mapper) {
        if (currentValue == null) {
            JsonNode fieldNode = jsonNode.path(fieldName);
            if (fieldNode.isMissingNode() || fieldNode.isNull()) {
                return null;
            }
            return mapper.apply(fieldNode);
        }
        return currentValue;
    }

    /**
     * Generic lazy loader that uses a supplier function.
     * Useful for complex loading logic including network requests.
     *
     * @param <T>          the type of value
     * @param currentValue the current value
     * @param loader       the supplier function to load the value
     * @return the current value if not null, otherwise the result from loader
     */
    public <T> T load(T currentValue, Supplier<T> loader) {
        if (currentValue == null) {
            return loader.get();
        }
        return currentValue;
    }

    /**
     * Loads an object if the current value is null and the field exists and is not null.
     * Uses a supplier function for object creation.
     *
     * @param <T>          the type of the value to load
     * @param currentValue the current value (may be null)
     * @param fieldName    the name of the field in the JSON node
     * @param supplier     function that provides the value when the field exists and is not null
     * @return the current value if not null, otherwise the supplied value if field exists and is not null, otherwise null
     */
    public <T> T loadIfExistsAndNonNull(T currentValue, String fieldName, Supplier<T> supplier) {
        if (currentValue == null && jsonNode.has(fieldName) && jsonNode.hasNonNull(fieldName)) {
            return supplier.get();
        }
        return currentValue;
    }

    /**
     * Loads a value from JSON only if the field exists and current value is null.
     *
     * @param <T>          the type of the value to load
     * @param currentValue the current value (may be null)
     * @param fieldName    the name of the field in the JSON node
     * @param loader       function that provides the value when the field exists
     * @return the current value if not null, otherwise the loaded value if field exists, otherwise null
     */
    public <T> T loadIfExists(T currentValue, String fieldName, Function<JsonNode, T> loader) {
        if (currentValue == null && jsonNode.has(fieldName)) {
            return loader.apply(jsonNode.path(fieldName));
        }
        return currentValue;
    }

    /**
     * Loads a value only if the field exists and current value is null.
     * Uses a supplier function for complex object creation.
     *
     * @param <T>          the type of the value to load
     * @param currentValue the current value (may be null)
     * @param fieldName    the name of the field in the JSON node
     * @param supplier     function that provides the value when the field exists
     * @return the current value if not null, otherwise the supplied value if field exists, otherwise null
     */
    public <T> T loadIfExists(T currentValue, String fieldName, Supplier<T> supplier) {
        if (currentValue == null && jsonNode.has(fieldName)) {
            return supplier.get();
        }
        return currentValue;
    }
}
