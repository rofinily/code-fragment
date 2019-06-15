package me.anchore.draft.util;

public class StackUtil {
    public static StackTraceElement getCaller() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return stackTrace[stackTrace.length - 2];
    }

    static StackTraceElement getCaller(int depth) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return stackTrace[stackTrace.length - 1 - depth];
    }
}
