package anc.io.util;


import anc.io.Releasable;
import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

import java.io.Closeable;
import java.io.IOException;
import java.nio.MappedByteBuffer;

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
                e.printStackTrace();
            }
        }
    }

    public static void release(Releasable releasable) {
        if (releasable != null) {
            releasable.release();
        }
    }

    public static void release(Releasable... releasables) {
        for (Releasable releasable : releasables) {
            release(releasable);
        }
    }

    public static void release(MappedByteBuffer buf) {
        if (buf != null) {
            buf.force();
            Cleaner cleaner = ((DirectBuffer) buf).cleaner();
            cleaner.clean();
            cleaner.clear();
        }
    }
}