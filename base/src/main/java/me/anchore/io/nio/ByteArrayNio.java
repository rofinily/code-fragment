package me.anchore.io.nio;

import me.anchore.io.ByteIo;
import me.anchore.io.IntIo;
import me.anchore.io.Io;
import me.anchore.io.LongIo;
import me.anchore.io.nio.NioConf.IoType;
import me.anchore.io.util.IoUtil;

/**
 * @author anchore
 * @date 2018/7/20
 */
public class ByteArrayNio extends BaseNio implements Io<byte[]> {
    private LongIo position;
    private IntIo length;
    private ByteIo data;

    private long currentPos;

    public ByteArrayNio(NioConf conf) {
        super(conf);
        init();
    }

    private void init() {
        position = new LongNio(conf.ofAnotherPath(String.format("%s/%s", conf.getPath(), "pos")));
        length = new IntNio(conf.ofAnotherPath(String.format("%s/%s", conf.getPath(), "len")));
        data = new ByteNio(conf.ofAnotherPath(String.format("%s/%s", conf.getPath(), "data")));
        if (conf.isAppend()) {
            LongIo lastPosition = new LongNio(new NioConf(
                    String.format("%s/%s", conf.getPath(), "last_pos"), IoType.READ, conf.getPageSize(), conf.getFileSize(), conf.isMapped()));
            currentPos = lastPosition.isReadable() ? lastPosition.get(0) : 0;
            lastPosition.release();
        }
    }

    @Override
    public byte[] get(long pos) {
        long start = position.get(pos);
        int size = length.get(pos);
        byte[] bytes = new byte[size];
        for (long i = 0; i < size; i++) {
            bytes[(int) i] = data.get(start + i);
        }
        return bytes;
    }

    @Override
    public void put(long pos, byte[] val) {
        position.put(pos, currentPos);
        length.put(pos, val.length);
        for (byte b : val) {
            data.put(currentPos++, b);
        }
    }

    @Override
    public boolean isReadable() {
        return position != null && position.isReadable() &&
                length != null && length.isReadable() &&
                data != null && data.isReadable();
    }

    @Override
    public void release() {
        if (conf.isAppend()) {
            LongIo lastPosition = new LongNio(new NioConf(
                    String.format("%s/%s", conf.getPath(), "last_pos"), IoType.OVERWRITE, conf.getPageSize(), conf.getFileSize(), conf.isMapped()));
            lastPosition.put(0, currentPos);
            lastPosition.release();
        }
        IoUtil.release(data, position, length);
        data = null;
        position = null;
        length = null;
    }
}