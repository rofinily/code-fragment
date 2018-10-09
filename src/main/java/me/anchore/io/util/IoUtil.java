package me.anchore.io.util;

import me.anchore.io.Releasable;
import me.anchore.log.Loggers;
import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;

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

    public static void release(final ByteBuffer buf) {
        if (buf != null && buf.isDirect()) {
            AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
                Cleaner cleaner = ((DirectBuffer) buf).cleaner();
                if (cleaner != null) {
                    cleaner.clean();
                    cleaner.clear();
                }
                return null;
            });
        }
    }
}