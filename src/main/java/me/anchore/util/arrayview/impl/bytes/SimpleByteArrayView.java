package me.anchore.util.arrayview.impl.bytes;

import me.anchore.util.Assert;
import me.anchore.util.arrayview.ByteArrayView;

/**
 * @author anchore
 * @date 2018/10/16
 */
public class SimpleByteArrayView implements ByteArrayView {

    private byte[] bytes;

    public SimpleByteArrayView(byte... bytes) {
        Assert.notNull(bytes);
        this.bytes = bytes;
    }

    @Override
    public byte getByte(int index) {
        return bytes[index];
    }

    @Override
    public byte setByte(int index, byte b) {
        byte prev = bytes[index];
        bytes[index] = b;
        return prev;
    }

    @Override
    public Byte get(int index) {
        return getByte(index);
    }

    @Override
    public int length() {
        return bytes.length;
    }

    @Override
    public Byte set(int index, Byte aByte) {
        return setByte(index, aByte);
    }
}