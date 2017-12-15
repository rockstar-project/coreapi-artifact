package com.ibmcloud.kickster.util;

import java.util.Collection;
import java.util.Map;

import org.springframework.util.StringUtils;

public final class CheckUtils {

    public static void checkArgument(boolean condition, String errorMessage) {
        if (!condition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void checkArgumentsNotNull(Object... arguments) {
        for (Object argument : arguments) {
            checkArgumentNotNull(argument);
        }
    }

    public static void checkArgumentNotNull(Object argument) {
        checkArgument(argument != null, argument.getClass().getSimpleName() + " must not be null");
    }

    public static void checkArgumentNotEmpty(String argument) {
        checkArgument(!StringUtils.isEmpty(argument),
                "String argument must not be null or empty");
    }
    
    public static void checkArgumentNotNullOrEmpty(String argument) {
        checkArgument(argument != null && !argument.isEmpty(),
                "String argument must not be null or empty");
    }

    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

}