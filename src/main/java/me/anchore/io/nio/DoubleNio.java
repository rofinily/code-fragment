package me.anchore.io.nio;


import me.anchore.io.DoubleIo;

/**
 * @author anchore
 * @date 2018/7/21
 */
public class DoubleNio extends BaseAtomNio<Double> implements DoubleIo {
    public DoubleNio(NioConf conf) {
        super(conf);
    }

    @Override
    int getStep() {
        return 3;
    }

    @Override
    public Double get(long pos) {
        return getDouble(pos);
    }

    @Override
    public void put(long pos, Double val) {
        putDouble(pos, val);
    }

    @Override
    public double getDouble(long pos) {
        initBuf(getPage(pos));
        return buf.getDouble(getOffset(pos));
    }

    @Override
    public void putDouble(long pos, double val) {
        initBuf(getPage(pos));
        int offset = getOffset(pos);
        buf.putDouble(offset, val);

        setBufPosition(offset);
    }
}