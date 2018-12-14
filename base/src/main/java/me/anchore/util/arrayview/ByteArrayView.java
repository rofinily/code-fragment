package me.anchore.util.arrayview;

import me.anchore.util.function.ByteConsumer;

/**
 * @author anchore
 * @date 2018/10/16
 */
public interface ByteArrayView extends ArrayView<Byte> {

    byte getByte(int index);

    byte setByte(int index, byte b);

    default void forEachByte(ByteConsumer consumer) {
        for (int i = 0; i < length(); i++) {
            consumer.accept(getByte(i));
        }
    }
}