package org.dimas4ek.wrapper.utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EnumUtils {
    public static <T extends Enum<T>> T getEnumObjectFromInt(int value, Class<T> enumClass) {
        for (T enumConstant : enumClass.getEnumConstants()) {
            if (value == enumConstant.ordinal()) {
                return enumConstant;
            }
        }
        return null;
    }

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
