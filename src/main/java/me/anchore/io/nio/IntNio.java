package me.anchore.io.nio;


import me.anchore.io.IntIo;

/**
 * @author anchore
 * @date 2018/7/21
 */
public class IntNio extends BaseAtomNio<Integer> implements IntIo {
    public IntNio(NioConf conf) {
        super(conf);
    }

    @Override
    int getStep() {
        return 2;
    }

    @Override
    public Integer get(long pos) {
        return getInt(pos);
    }

    @Override
    public void put(long pos, Integer val) {
        putInt(pos, val);
    }

    @Override
    public int getInt(long pos) {
        initBuf(getPage(pos));
        return buf.getInt(getOffset(pos));
    }

    @Override
    public void putInt(long pos, int val) {
        initBuf(getPage(pos));
        int offset = getOffset(pos);
        buf.putInt(offset, val);

        setBufPosition(offset);
    }
}