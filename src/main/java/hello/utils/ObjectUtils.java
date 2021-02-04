package hello.utils;

public class ObjectUtils {

    public static String getOrDefaultStr(Object object) {
        return object != null ? object.toString() : "";
    }

    public static int getOrDefaultInt(Object object) {
        return object != null ? Integer.valueOf(object.toString()) : 0;
    }

    public static long getOrDefaultLong(Object object) {
        return object != null ? Long.valueOf(object.toString()) : 0L;
    }
}
