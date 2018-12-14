package me.anchore.util;

/**
 * @author anchore
 * @date 2018/10/9
 */
public class Checker {
    public static boolean in(Object check, Object... os) {
        if (check == null) {
            return false;
        }
        for (Object o : os) {
            if (check == o || check.equals(o)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean isEmpty(T[] ts) {
        return ts == null || ts.length == 0;
    }

    public static boolean equals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null) {
            return false;
        }
        return o1.equals(o2);
    }
}