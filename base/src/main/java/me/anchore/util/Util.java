package me.anchore.util;

import java.util.function.Consumer;

/**
 * @author anchore
 */
public final class Util {

    public static <T> void ifPresent(T t, Consumer<T> consumer) {
        if (t != null) {
            consumer.accept(t);
        }
    }

    public static int hashCode(Object o) {
        return o == null ? 0 : o.hashCode();
    }

    private static Throwable getRootCause(Throwable t) {
        Throwable cause = t.getCause();
        if (cause == null) {
            return t;
        }
        return getRootCause(cause);
    }

    public static String getRootCauseMessage(Throwable t) {
        Throwable rootCause = getRootCause(t);
        String message = rootCause.getMessage();
        String simpleThrowable = rootCause.getClass().getSimpleName();
        if (message == null) {
            return simpleThrowable;
        }
        return String.format("%s: %s", simpleThrowable, message);
    }
}
