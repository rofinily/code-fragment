package me.anchore.util;

/**
 * @author anchore
 * @date 2018/7/11
 */
public final class Assert {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isFalse(boolean expression) {
        isFalse(expression, "[Assertion failed] - this expression must be false");
    }

    public static <T> void notNull(T t, String message) {
        if (t == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static <T> void notNull(T t) {
        notNull(t, "[Assertion failed] - this argument is required; it must not be null");
    }

    public static <T> void allNotNull(T[] ts) {
        for (T t : ts) {
            notNull(t);
        }
    }

    private Assert() {
        throw new InstantiationError();
    }
}