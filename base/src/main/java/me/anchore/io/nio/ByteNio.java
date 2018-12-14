package me.anchore.io.nio;


import me.anchore.io.ByteIo;

/**
 * @author anchore
 * @date 2018/7/20
 */
public class ByteNio extends BaseAtomNio<Byte> implements ByteIo {
    public ByteNio(NioConf conf) {
        super(conf);
    }

    @Override
    public Byte get(long pos) {
        return getByte(pos);
    }

    @Override
    public void put(long pos, Byte val) {
        putByte(pos, val);
    }

    @Override
    public byte getByte(long pos) {
        initBuf(getPage(pos));
        return buf.get(getOffset(pos));
    }

    @Override
    public void putByte(long pos, byte val) {
        initBuf(getPage(pos));
        int offset = getOffset(pos);
        buf.put(offset, val);

        setBufPosition(offset);
    }

    @Override
    int getStep() {
        return 0;
    }
}