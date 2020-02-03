package me.anchore.util;

import me.anchore.io.Releasable;
import me.anchore.log.Loggers;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author anchore
 * @date 2018/7/19
 */
public class IoUtil {
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Loggers.getLogger().error(e);
            }
        }
    }

    public static void close(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                close(closeable);
            }
        }
    }

    public static void release(Releasable releasable) {
        if (releasable != null) {
            releasable.release();
        }
    }

    public static void release(Releasable... releasables) {
        if (releasables != null) {
            for (Releasable releasable : releasables) {
                release(releasable);
            }
        }
    }
}