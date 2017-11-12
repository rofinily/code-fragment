package anc.util;

import java.util.function.IntFunction;

/**
 * @author anchore
 * @date 2017/11/12
 */
public class ArrayNewer {

    public static <T> T[] newArray(IntFunction<T[]> constructor, int length) {
        return constructor.apply(length);
    }

    public static byte[] newByteArray(IntFunction<byte[]> constructor, int length) {
        return constructor.apply(length);
    }

}
