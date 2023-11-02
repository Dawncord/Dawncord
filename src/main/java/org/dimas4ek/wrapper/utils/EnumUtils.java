package org.dimas4ek.wrapper.utils;

import org.json.JSONObject;

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

    public static <T extends Enum<T>> T getEnumObject(JSONObject json, String key, Class<T> enumClass) {
        int intValue = json.optInt(key, -1);
        if (intValue >= 0 && intValue < enumClass.getEnumConstants().length) {
            return enumClass.getEnumConstants()[intValue];
        }
        return null;
    }

    public static <T extends Enum<T>> List<T> getEnumListFromLong(JSONObject json, String key, Class<T> enumClass) {
        long flagsFromJson = json.optLong(key, 0);
        List<T> flags = new ArrayList<>();

        for (T flag : enumClass.getEnumConstants()) {
            if ((flagsFromJson & (1L << flag.ordinal())) != 0) {
                flags.add(flag);
            }
        }

        return flags;
    }

}
