package me.anchore.io.nio;

import me.anchore.io.LongIo;

/**
 * @author anchore
 * @date 2018/7/20
 */
public class LongNio extends BaseAtomNio<Long> implements LongIo {
    public LongNio(NioConf conf) {
        super(conf);
    }

    @Override
    int getStep() {
        return 3;
    }

    @Override
    public Long get(long pos) {
        return getLong(pos);
    }

    @Override
    public void put(long pos, Long val) {
        putLong(pos, val);
    }

    @Override
    public long getLong(long pos) {
        initBuf(getPage(pos));
        return buf.getLong(getOffset(pos));
    }

    @Override
    public void putLong(long pos, long val) {
        initBuf(getPage(pos));
        int offset = getOffset(pos);
        buf.putLong(offset, val);

        setBufPosition(offset);
    }
}