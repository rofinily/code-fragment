package me.anchore.util.arrayview.impl.bytes;

import me.anchore.util.arrayview.ByteArrayView;
import me.anchore.util.arrayview.impl.BaseArrayView;

/**
 * @author anchore
 * @date 2018/10/16
 */
public class SingletonByteArrayView extends BaseArrayView<Byte> implements ByteArrayView {

    private byte singleton;

    public SingletonByteArrayView(byte singleton) {
        this.singleton = singleton;
    }

    @Override
    public byte getByte(int index) {
        checkBound(index);
        return singleton;
    }

    @Override
    public byte setByte(int index, byte t) {
        checkBound(index);
        byte prev = singleton;
        singleton = t;
        return prev;
    }

    @Override
    public Byte get(int index) {
        return getByte(index);
    }

    @Override
    public Byte set(int index, Byte aByte) {
        return setByte(index, aByte);
    }

    @Override
    public int length() {
        return 1;
    }
}