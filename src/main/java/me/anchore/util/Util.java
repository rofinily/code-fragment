package me.anchore.util;

public class Util {

    public static <T> void requireNonNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
    }

    public static <T> void requireNonNull(T[] ts) {
        for (T t : ts) {
            requireNonNull(t);
        }
    }

    @SafeVarargs
    public static <T> void requireNonNull(T t0, T... ts) {
        requireNonNull(t0);
        requireNonNull(ts);
    }

}