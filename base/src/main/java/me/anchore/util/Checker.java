package me.anchore.util;

import java.util.Objects;

/**
 * @author anchore
 * @date 2018/10/9
 */
public class Checker {
    public static boolean in(Object check, Object... os) {
        for (Object o : os) {
            if (Objects.equals(check, o)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean isEmpty(T[] ts) {
        return ts == null || ts.length == 0;
    }
}