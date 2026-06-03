package utils;

import java.util.HashMap;
import java.util.Map;

public final class ScenarioContext {
    private static final ThreadLocal<Map<String, Object>> CONTEXT = ThreadLocal.withInitial(HashMap::new);

    private ScenarioContext() {
    }

    public static void put(String key, Object value) {
        CONTEXT.get().put(key, value);
    }

    public static Object get(String key) {
        return CONTEXT.get().get(key);
    }

    public static String getString(String key) {
        Object value = get(key);
        return value == null ? null : String.valueOf(value);
    }

    public static Integer getInteger(String key) {
        Object value = get(key);
        if (value == null) {
            return null;
        }
        return value instanceof Number ? ((Number) value).intValue() : Integer.parseInt(String.valueOf(value));
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
