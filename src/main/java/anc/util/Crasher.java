package anc.util;

/**
 * @author anchore
 */
public class Crasher {
    public static void crash(String msg, Throwable t) {
        throw new RuntimeException(msg, t);
    }

    public static void crash(String msg) {
        Util.requireNonNull(msg);
        crash(msg, null);
    }

    public static void crash(Throwable t) {
        Util.requireNonNull(t);
        crash(null, t);
    }

}
